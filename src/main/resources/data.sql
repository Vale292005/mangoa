-- 1. REGISTRO DE USUARIOS
INSERT INTO users (id, email, password_hash, role, first_name, last_name, created_at, update_at, version)
SELECT '11111111-1111-4111-8111-111111111111', 'juan@example.com', '$2a$10$abcdefghijklmnopqrstuv', 'CUSTOMER', 'Juan', 'Pérez', NOW(), NOW(), 0
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'juan@example.com');

INSERT INTO users (id, email, password_hash, role, first_name, last_name, created_at, update_at, version)
SELECT '22222222-2222-4222-8222-222222222222', 'maria@example.com', '$2a$10$abcdefghijklmnopqrstuv', 'CUSTOMER', 'María', 'López', NOW(), NOW(), 0
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'maria@example.com');

INSERT INTO users (id, email, password_hash, role, first_name, last_name, created_at, update_at, version)
SELECT '33333333-3333-4333-8333-333333333333', 'pedro@example.com', '$2a$10$abcdefghijklmnopqrstuv', 'CUSTOMER', 'Pedro', 'García', NOW(), NOW(), 0
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'pedro@example.com');

INSERT INTO users (id, email, password_hash, role, first_name, last_name, created_at, update_at, version)
SELECT '44444444-4444-4444-8444-444444444444', 'ana@example.com', '$2a$10$abcdefghijklmnopqrstuv', 'CUSTOMER', 'Ana', 'Martín', NOW(), NOW(), 0
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'ana@example.com');

INSERT INTO users (id, email, password_hash, role, first_name, last_name, created_at, update_at, version)
SELECT '55555555-5555-4555-8555-555555555555', 'admin@example.com', '$2a$10$abcdefghijklmnopqrstuv', 'ADMIN', 'Admin', 'Sistema', NOW(), NOW(), 0
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@example.com');


-- 2. ALOJAMIENTO 1: Apartamento Centro (Sevilla)
INSERT INTO accommodations (id, name, description, location, price, capacity, active, created_at, updated_at, version)
SELECT 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa', 'Apartamento Centro', 'Apartamento moderno en el centro con wifi y cocina.', 'Sevilla', 120.00, 4, true, NOW(), NOW(), 0
WHERE NOT EXISTS (SELECT 1 FROM accommodations WHERE id = 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa');

INSERT INTO accommodation_images (accommodation_id, ruta)
SELECT 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa', '/images/apt_centro_1.jpg'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_images WHERE accommodation_id = 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa' AND ruta = '/images/apt_centro_1.jpg');

INSERT INTO accommodation_images (accommodation_id, ruta)
SELECT 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa', '/images/apt_centro_2.jpg'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_images WHERE accommodation_id = 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa' AND ruta = '/images/apt_centro_2.jpg');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
SELECT 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa', 'WIFI'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_amenities WHERE accommodation_id = 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa' AND amenity = 'WIFI');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
SELECT 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa', 'TV'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_amenities WHERE accommodation_id = 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa' AND amenity = 'TV');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
SELECT 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa', 'CLIMATIZACION'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_amenities WHERE accommodation_id = 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa' AND amenity = 'CLIMATIZACION');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
SELECT 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa', 'BANIO_PRIVADO'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_amenities WHERE accommodation_id = 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa' AND amenity = 'BANIO_PRIVADO');


-- 3. ALOJAMIENTO 2: Casa Rural (Granada)
INSERT INTO accommodations (id, name, description, location, price, capacity, active, created_at, updated_at, version)
SELECT 'bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb', 'Casa Rural', 'Casa tranquila con jardín y vistas al campo.', 'Granada', 180.00, 6, true, NOW(), NOW(), 0
WHERE NOT EXISTS (SELECT 1 FROM accommodations WHERE id = 'bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb');

INSERT INTO accommodation_images (accommodation_id, ruta)
SELECT 'bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb', '/images/casa_rural_1.jpg'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_images WHERE accommodation_id = 'bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb' AND ruta = '/images/casa_rural_1.jpg');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
SELECT 'bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb', 'JARDIN'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_amenities WHERE accommodation_id = 'bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb' AND amenity = 'JARDIN');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
SELECT 'bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb', 'PISCINA'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_amenities WHERE accommodation_id = 'bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb' AND amenity = 'PISCINA');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
SELECT 'bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb', 'PARQUEADERO'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_amenities WHERE accommodation_id = 'bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb' AND amenity = 'PARQUEADERO');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
SELECT 'bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb', 'PET_FRIENDLY'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_amenities WHERE accommodation_id = 'bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb' AND amenity = 'PET_FRIENDLY');


-- 4. ALOJAMIENTO 3: Loft Playa (Málaga)
INSERT INTO accommodations (id, name, description, location, price, capacity, active, created_at, updated_at, version)
SELECT 'cccccccc-cccc-4ccc-8ccc-cccccccccccc', 'Loft Playa', 'Loft con terraza muy cerca de la playa.', 'Málaga', 150.00, 3, true, NOW(), NOW(), 0
WHERE NOT EXISTS (SELECT 1 FROM accommodations WHERE id = 'cccccccc-cccc-4ccc-8ccc-cccccccccccc');

INSERT INTO accommodation_images (accommodation_id, ruta)
SELECT 'cccccccc-cccc-4ccc-8ccc-cccccccccccc', '/images/loft_playa_1.jpg'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_images WHERE accommodation_id = 'cccccccc-cccc-4ccc-8ccc-cccccccccccc' AND ruta = '/images/loft_playa_1.jpg');

INSERT INTO accommodation_images (accommodation_id, ruta)
SELECT 'cccccccc-cccc-4ccc-8ccc-cccccccccccc', '/images/loft_playa_2.jpg'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_images WHERE accommodation_id = 'cccccccc-cccc-4ccc-8ccc-cccccccccccc' AND ruta = '/images/loft_playa_2.jpg');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
SELECT 'cccccccc-cccc-4ccc-8ccc-cccccccccccc', 'BALCON'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_amenities WHERE accommodation_id = 'cccccccc-cccc-4ccc-8ccc-cccccccccccc' AND amenity = 'BALCON');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
SELECT 'cccccccc-cccc-4ccc-8ccc-cccccccccccc', 'WIFI'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_amenities WHERE accommodation_id = 'cccccccc-cccc-4ccc-8ccc-cccccccccccc' AND amenity = 'WIFI');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
SELECT 'cccccccc-cccc-4ccc-8ccc-cccccccccccc', 'TV'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_amenities WHERE accommodation_id = 'cccccccc-cccc-4ccc-8ccc-cccccccccccc' AND amenity = 'TV');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
SELECT 'cccccccc-cccc-4ccc-8ccc-cccccccccccc', 'CLIMATIZACION'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_amenities WHERE accommodation_id = 'cccccccc-cccc-4ccc-8ccc-cccccccccccc' AND amenity = 'CLIMATIZACION');


-- 5. ALOJAMIENTO 4: Apartamento Montaña (Cádiz) - COMPLETADO
INSERT INTO accommodations (id, name, description, location, price, capacity, active, created_at, updated_at, version)
SELECT 'dddddddd-dddd-4ddd-8ddd-dddddddddddd', 'Apartamento Montaña', 'Ideal para escapadas con vistas panorámicas.', 'Cádiz', 210.00, 5, true, NOW(), NOW(), 0
WHERE NOT EXISTS (SELECT 1 FROM accommodations WHERE id = 'dddddddd-dddd-4ddd-8ddd-dddddddddddd');

INSERT INTO accommodation_images (accommodation_id, ruta)
SELECT 'dddddddd-dddd-4ddd-8ddd-dddddddddddd', '/images/apt_montana_1.jpg'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_images WHERE accommodation_id = 'dddddddd-dddd-4ddd-8ddd-dddddddddddd' AND ruta = '/images/apt_montana_1.jpg');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
SELECT 'dddddddd-dddd-4ddd-8ddd-dddddddddddd', 'MONTANIA'
WHERE NOT EXISTS (SELECT 1 FROM accommodation_amenities WHERE accommodation_id = 'dddddddd-dddd-4ddd-8ddd-dddddddddddd' AND amenity = 'MONTANIA');
