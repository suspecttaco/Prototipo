USE cafeteria;

INSERT INTO tamano (nombre, precio)
VALUES
    ('Chico', 0.00),
    ('Mediano', 5.00),
    ('Grande', 10.00),
    ('Jumbo', 15.00);

INSERT INTO sabor (nombre, precio)
VALUES
    ('Regular', 0.0),
    ('Vainilla', 5.00),
    ('Chocolate', 5.50),
    ('Caramelo', 6.00),
    ('Avellana', 6.50),
    ('Fresa', 5.50),
    ('Coco', 6.00),
    ('Almendra', 6.50),
    ('Canela', 5.25);

INSERT INTO producto (nombre, precio, descripcion, imagen_ruta)
VALUES
    ('Latte', 60.00, 'Café con leche vaporizada', '/products/latte.png'),
    ('Americano', 35.00, 'Café negro intenso', '/products/americano.png'),
    ('Capuccino', 50.00, 'Espuma de leche con café', '/products/capuchino.png'),
    ('Frappé', 75.00, 'Café frío batido', '/products/frappe.png'),
    ('Mocca', 70.00, 'Café con chocolate', '/products/moca.png'),
    ('Té Chai', 55.00, 'Infusión de especias y leche', '/products/chai.png'),
    ('Cold Brew', 50.00, 'Café infusionado en frío', '/products/coldbrew.png'),
    ('Matcha Latte', 75.00, 'Té verde matcha con leche', '/products/matchalatte.png'),
    ('Expresso', 30.00, 'Doble carga de café puro', '/products/espresso.png'),
    ('Chocolate Caliente', 65.00, 'Bebida caliente de cacao', '/products/chocolatecaliente.png');