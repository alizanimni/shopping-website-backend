-- Create users table
CREATE TABLE users (
    id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(255),
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);


INSERT INTO users (first_name, last_name, email, phone, address, username, password) VALUES
('Miki', 'Gabay', 'mikigabay@gmail.com', '0585236376', '45 Ben Eliezer St', 'mikigabay', '$2a$10$24P9JHWZJm8yRsJRpP4a.e11OvU9ynMvz6XAKJOrxl8Nhph7mojJ2'),
('Amitay', 'Gabay', 'amitaygabay1@gmail.com', '0504380333', '38 Erez St', 'amitaygabay', '$2a$10$K78Qy75RrDNQcAolPojuM.sI.otXpP23xhZYJ7p2fXrIMoI.k2ehO');

CREATE TABLE items (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    photo VARCHAR(255) NOT NULL,
    price DECIMAL NOT NULL,
    item_quantity INT NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO items(name, photo, price, item_quantity) VALUES
('Smart TV 55" 4K', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQhH0Z2ozN0h7TtrnpNXYGtqivbSQXifm5Bww&s', 3200.99, 25),
('Wireless Bluetooth Headphones', 'https://i5.walmartimages.com/asr/375d2852-d985-4aa9-b69c-b686da6e513f_1.eef652ba8620bde5c2b792e0ce74f113.jpeg', 499.99, 40),
('Gaming Laptop GTX 4060', 'https://m.media-amazon.com/images/I/61ng0ZwzeqL.jpg', 5899.00, 15),
('Smartphone Pro Max 256GB', 'https://cellcomshop.cellcom.co.il/media/catalog/product/cache/3debabbfee868758c6d5de60457f9893/f/i/file_510_1.png', 4299.90, 30),
('Robot Vacuum Cleaner', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9ZzpTfI6_aFmK9X0V6jFxQCEtiUnnaqgtoA&s', 1199.50, 20),
('Espresso Coffee Machine', 'https://m.media-amazon.com/images/I/6190zcm9RVL.jpg', 899.99, 35),
('Air Fryer 5L', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcReYqupCc7ConzEv_XrCVgYXDtoZO23NYGV0w&s', 549.00, 50),
('Portable Speaker 50W', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwpFOf8xENMZ0ej3ya0g33RnzQdfIg6vVFzg&s', 699.90, 28),
('Smartwatch Series 9', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGKvM5iBmjKDZoN8mE-fOUewnZ-TihtGh1iw&s', 1199.00, 22),
('Wireless Charging Station', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcThpBpA08P_b0FEtYOLHSAHgFoOBqEzHXSYbw&s', 299.99, 60),
('Dishwasher Stainless Steel', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQvH3z2hCgTeUBEjTjY4CvifYbCLP8I0nSyZg&s', 2899.00, 18),
('Microwave Oven 30L', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjQf2IBsqUqx_IqipPprtavhsQ_xrz9e3Q1Q&s', 749.99, 35),
('Induction Cooktop 4 Burners', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSw0v2X6cma3LzPpHN6uwIOsWvPc0rNunbQew&s', 3299.90, 12),
('Smart Refrigerator 600L', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRW7fiRu6Hz6lO72aUjj-sZxc0vWzert-39w&s', 7999.00, 10),
('Electric Kettle 1.7L', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT7RzlLtnq86pd9NHDdBkT32C6R_oRKqDasSw&s', 199.99, 50),
('Hair Dryer Professional 2000W', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRH5KPDHlTBxLinf3bKN27OZvSb4jLKOyZtlg&s', 349.00, 40),
('Steam Iron Pro', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQejo4tAj03B7b9wEAkIwnLiHgjQptl7pQyng&s', 269.99, 45),
('Security Camera 1080p', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6zZu8j1G13lzKdu9ovHiaGn8i-f9wx9y8mQ&s', 599.00, 25),
('Electric Toothbrush', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwij01SUwILEPWwoI2y8GhBNn6ekNhcpGIFg&s', 399.90, 30),
('Smart Home Hub', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJbTh8X-jHaPraptQ1ApJSSvygpflEJwZb0Q&s', 799.99, 15);

CREATE TABLE favorite_items(
    user_id INT NOT NULL,
    item_id INT NOT NULL,
    PRIMARY KEY(user_id,item_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (item_id) REFERENCES items(id)
);

CREATE TABLE orders(
    id INT AUTO_INCREMENT,
    user_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    shipping_address VARCHAR(255) NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    status VARCHAR(10) DEFAULT 'OPEN',
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE ordered_items (
    item_id INT NOT NULL,
    order_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (order_id, item_id),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (item_id) REFERENCES items(id)
);











