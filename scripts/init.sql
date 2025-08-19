-- Script de inicialización para la base de datos
-- Este script se ejecuta automáticamente al levantar el contenedor de PostgreSQL

-- Crear base de datos de desarrollo
CREATE DATABASE spring30days_dev;

-- Crear base de datos de producción
CREATE DATABASE spring30days_prod;

-- Conectar a la base de datos principal
\c spring30days;

-- Crear extensiones útiles
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pg_trgm";

-- Crear índices para mejorar performance
-- (Se crearán automáticamente con JPA, pero aquí podemos añadir índices específicos)

-- Crear funciones de utilidad
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Comentarios para documentación
COMMENT ON DATABASE spring30days IS 'Base de datos para el desafío de 30 días de Spring Boot';
COMMENT ON DATABASE spring30days_dev IS 'Base de datos de desarrollo';
COMMENT ON DATABASE spring30days_prod IS 'Base de datos de producción';
