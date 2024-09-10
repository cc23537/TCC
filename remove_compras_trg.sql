USE tcc;

DELIMITER //

CREATE TRIGGER remove_from_compras AFTER INSERT ON tcc_alimentos
FOR EACH ROW
BEGIN
    DELETE FROM tcc_Compras WHERE alimentoASerComprado = NEW.nome_alimento;
END //

DELIMITER ;
