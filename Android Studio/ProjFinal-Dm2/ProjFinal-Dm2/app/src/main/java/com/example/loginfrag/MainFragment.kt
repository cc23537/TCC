package com.example.loginfrag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginfrag.databinding.FragmentMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class MainFragment : Fragment() {


    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    private var currentUser: FirebaseUser? = null

    private lateinit var disciplinaList: ArrayList<Disciplinas>
    private lateinit var db: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onStop() {

        if (currentUser != null) {
            auth.signOut()
        }
        super.onStop()
    }
    override fun onDestroyView() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            auth.signOut()
        }

        super.onDestroyView()
    }
    override fun onDestroy() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            auth.signOut()
        }


        _binding = null
        super.onDestroy()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        currentUser = auth.currentUser

        db = FirebaseDatabase.getInstance().getReference("Diciplinas")
        disciplinaList = arrayListOf()

        listagem()
        deletar()

        binding.rvDiciplinas.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }

        binding.ivSeta.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_mainFragment_to_navigation)
        }

        binding.floatingActionButton.setOnClickListener{
            val add = AddDialog()
            add.show(parentFragmentManager, "AddDialog")

            //findNavController().navigate(R.id.action_mainFragment_to_addFragment)
        }
    }


    // Metodo de deletar as Disciplinas
    private fun deletar(){
        val swipeBackground = ColorDrawable(Color.RED)
        val deleteIcon: Drawable? = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)

        val swipe = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.rvDiciplinas.adapter as RvItens
                adapter.deleteItem(viewHolder.adapterPosition, requireContext())
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                val itemView = viewHolder.itemView
                val backgroundCornerOffset = 20

                val paint = Paint().apply {
                    color = Color.rgb(253, 105,105)
                    isAntiAlias = true
                }

                val rectF = RectF(
                    itemView.right + dX - backgroundCornerOffset,
                    itemView.top.toFloat(),
                    itemView.right.toFloat(),
                    itemView.bottom.toFloat()
                )
                val cornerRadius = 30f

                if (dX < 0) {
                    c.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)

                    val iconMargin = (itemView.height - deleteIcon!!.intrinsicHeight) / 2
                    val iconTop = itemView.top + (itemView.height - deleteIcon.intrinsicHeight) / 2
                    val iconBottom = iconTop + deleteIcon.intrinsicHeight
                    val iconLeft = itemView.right - iconMargin - deleteIcon.intrinsicWidth
                    val iconRight = itemView.right - iconMargin
                    deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                    deleteIcon.draw(c)
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(binding.rvDiciplinas)
    }


    // Metodo das listagens das Disciplinas
    private fun listagem() {
        db.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                disciplinaList.clear()
                if(snapshot.exists()){
                    for(disciplinasSnap in snapshot.children){
                        val disciplina = disciplinasSnap.getValue(Disciplinas::class.java)
                        disciplinaList.add(disciplina!!)
                    }
                }

                val rvAdapter = RvItens(disciplinaList)
                binding.rvDiciplinas.adapter = rvAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Erro: $error", Toast.LENGTH_SHORT).show()
            }

        })
    }
}