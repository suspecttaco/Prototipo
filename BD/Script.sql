CREATE DATABASE cafeteria;
USE cafeteria;
CREATE TABLE tamano
(
    TamanoId    INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(20) NOT NULL,
    precio      FLOAT(5, 2)     NOT NULL,
    fecha_registro DATETIME DEFAULT NOW(),
    fecha_mod   TIMESTAMP DEFAULT NOW()
);
CREATE TABLE sabor
(
    SaborId        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nombre         VARCHAR(15)     NOT NULL,
    precio         FLOAT(4, 2)     NOT NULL,
    fecha_registro DATETIME  DEFAULT NOW(),
    fecha_mod      DATETIME  DEFAULT NOW()
);
CREATE TABLE producto
(
    ProductoId  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(20)     NOT NULL,
    precio      FLOAT(5, 2)     NOT NULL,
    descripcion VARCHAR(50)     NULL,
    imagen_ruta VARCHAR(255)    NOT NULL,
    fecha_mod   DATETIME DEFAULT NOW()
);
CREATE TABLE inventario
(
    InventarioId   INT PRIMARY KEY    NOT NULL AUTO_INCREMENT,
    ProductoId     INT                NOT NULL,
    INDEX FK_ProductoId (ProductoId),
    FOREIGN KEY (ProductoId) REFERENCES producto (ProductoId),
    nombre         VARCHAR(25)        NOT NULL,
    cantidad       INT                NOT NULL,
    estatus        ENUM ('A','D','E') NOT NULL, -- A -> Activo // D -> Desactivado // E -> Eliminado
    fecha_registro DATETIME  DEFAULT NOW(),
    fecha_mod      DATETIME  DEFAULT NOW()
);



CREATE TABLE usuario
(
    UsuarioId  INT PRIMARY KEY          NOT NULL AUTO_INCREMENT,
    contrasena CHAR(10)                 NOT NULL,
    rol        ENUM ('EMP','GER','ADM') NOT NULL,
    fecha_registro DATETIME  DEFAULT NOW()
);

CREATE TABLE cupon
(
    CuponId        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nombre         VARCHAR(25)     NOT NULL,
    porcentaje     FLOAT(4, 2)     NOT NULL,
    fecha_limite   DATE            NOT NULL,
    fecha_creado DATETIME DEFAULT NOW(),
    estado ENUM('A','E') NOT NULL -- A -> Activo // E -> Expirado
);
CREATE TABLE cliente
(
    ClienteId         INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nombre            VARCHAR(25)     NOT NULL,
    ape1              VARCHAR(50)     NOT NULL,
    ape2              VARCHAR(50)     NOT NULL,
    numero_telefonico CHAR(10)        NOT NULL,
    email             VARCHAR(50)     NULL,
    fecha_registro    DATETIME        DEFAULT NOW(),
    fecha_mod         DATETIME        DEFAULT NOW()
);
CREATE TABLE InicioSesion
(
    InicioId            INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    UsuarioId           INT             NOT NULL,
    INDEX FK_UsuarioId (UsuarioId),
    FOREIGN KEY (UsuarioId) REFERENCES usuario (UsuarioId),
    fecha_inicio_sesion DATETIME        NOT NULL
);
CREATE TABLE caja
(
    CajaId       INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    InicioId     INT             NOT NULL,
    INDEX FK_InicioId (InicioId),
    FOREIGN KEY (InicioId) REFERENCES InicioSesion (InicioId),
    UsuarioId    INT             NOT NULL,
    INDEX FK_UsuarioId (UsuarioId),
    FOREIGN KEY (UsuarioId) REFERENCES usuario (UsuarioId),
    nombre       VARCHAR(30)     NOT NULL,
    fecha_inicio DATETIME        DEFAULT NOW(),
    fecha_cierre DATETIME        DEFAULT NOW()
);
CREATE TABLE SoporteTecnico
(
    SoporteId     INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    UsuarioId     INT             NOT NULL,
    INDEX FK_UsuarioId (UsuarioId),
    FOREIGN KEY (UsuarioId) REFERENCES usuario (UsuarioId),
    descripcion   VARCHAR(50)     NOT NULL,
    fecha_reporte DATETIME        DEFAULT NOW(),
    fecha_mod     DATETIME       DEFAULT NOW()
);
CREATE TABLE ticket
(
    TicketId    INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    ClienteId   INT             NOT NULL,
    INDEX FK_ClienteId (ClienteId),
    FOREIGN KEY (ClienteId) REFERENCES cliente (ClienteId),
    importe     FLOAT(6, 2)     NOT NULL,
    CajaId      INT             NOT NULL,
    INDEX FK_CajaId (CajaId),
    FOREIGN KEY (CajaId) REFERENCES caja (CajaId),
    fecha_venta DATETIME        DEFAULT NOW(),
    fecha_mod   DATETIME        DEFAULT NOW()
);
CREATE TABLE Venta
(
    VentaId    INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    llevar     ENUM ('Y','N')  NOT NULL,
    UsuarioId  INT             NOT NULL,
    INDEX FK_UsuarioId (UsuarioId),
    FOREIGN KEY (UsuarioId) REFERENCES usuario (UsuarioId),
    CuponId    INT             NULL,
    INDEX FK_CuponId (CuponId),
    FOREIGN KEY (CuponId) REFERENCES cupon (CuponId),
    TicketId   INT             NOT NULL,
    INDEX FK_TicketId (TicketId),
    FOREIGN KEY (TicketId) REFERENCES Ticket (TicketId),
    TamanoId   INT             NOT NULL,
    INDEX FK_tamanoId (TamanoId),
    FOREIGN KEY (TamanoId) REFERENCES tamano (TamanoId),
    SaborId    INT             NOT NULL,
    INDEX FK_saborId (SaborId),
    FOREIGN KEY (SaborId) REFERENCES sabor (SaborId),
    ProductoId INT             NOT NULL,
    INDEX FK_ProductoId (ProductoId),
    FOREIGN KEY (ProductoId) REFERENCES producto (ProductoId),
    fecha_mod  DATETIME        NOT NULL
);