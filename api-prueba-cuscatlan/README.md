# 📦 API Prueba Cuscatlán

Repositorio para la API **api-prueba-cuscatlan**.

Es una API para gestionar órdenes de productos con sus respectivos detalles, así como para realizar el pago de las transacciones.

---

## ⚠️ Nota importante

> **Debe cambiar la palabra `USER_DATABASE` por el nombre de su base de datos.**

---

## 🔐 Autenticación

Antes de consumir los endpoints protegidos, se debe obtener el token JWT usando:

```
POST /api-prueba-cuscatlan/authentication/getToken
```

---

## 📑 Documentación Swagger

Puedes acceder a la documentación interactiva Swagger UI en:

```
http://localhost:8089/api-prueba-cuscatlan/swagger-ui/index.html
```

---

## 🗄️ Script de creación de base de datos en PostgreSQL

### 📌 Base de datos

```sql
CREATE DATABASE purchase_orders 
WITH OWNER = "USER_DATABASE" 
ENCODING = 'UTF8' 
LC_COLLATE = 'en_US.UTF-8' 
LC_CTYPE = 'en_US.UTF-8' 
TABLESPACE = pg_default 
CONNECTION LIMIT = -1 
IS_TEMPLATE = False;

GRANT TEMPORARY, CONNECT ON DATABASE purchase_orders TO PUBLIC;
GRANT ALL ON DATABASE purchase_orders TO "USER_DATABASE";
```

---

### 📌 Esquema `sch_purchase_order`

```sql
CREATE SCHEMA IF NOT EXISTS sch_purchase_order AUTHORIZATION "USER_DATABASE";

GRANT ALL ON SCHEMA sch_purchase_order TO "USER_DATABASE";

ALTER DEFAULT PRIVILEGES FOR ROLE USER_DATABASE IN SCHEMA sch_purchase_order 
GRANT INSERT, SELECT, UPDATE, DELETE, REFERENCES, TRIGGER ON TABLES TO "USER_DATABASE";
```

---

### 📌 Tabla `order_registration`

```sql
CREATE TABLE IF NOT EXISTS sch_purchase_order.order_registration (
    id_order INTEGER NOT NULL DEFAULT nextval('sch_purchase_order.order_registration_id_order_seq'::regclass),
    creation_date TIMESTAMP,
    modification_date TIMESTAMP,
    customer VARCHAR(250),
    order_status VARCHAR(50),
    payment_method VARCHAR(50),
    payment_status VARCHAR(50),
    total NUMERIC,
    CONSTRAINT order_registration_pkey PRIMARY KEY (id_order)
);

ALTER TABLE sch_purchase_order.order_registration OWNER TO postgres;

-- Triggers
CREATE OR REPLACE TRIGGER insert_order_date_order
BEFORE INSERT ON sch_purchase_order.order_registration
FOR EACH ROW
EXECUTE FUNCTION public.insert_current_timestamp_order_date_order();

CREATE OR REPLACE TRIGGER update_order_date_order
BEFORE UPDATE ON sch_purchase_order.order_registration
FOR EACH ROW
EXECUTE FUNCTION public.update_current_timestamp_order_date_order();
```

---

### 📌 Tabla `order_details`

```sql
CREATE TABLE IF NOT EXISTS sch_purchase_order.order_details (
    id_order_detail INTEGER NOT NULL DEFAULT nextval('sch_purchase_order.order_details_id_order_detail_seq'::regclass),
    id_order INTEGER,
    id_product INTEGER,
    quantity INTEGER,
    price NUMERIC,
    subtotal NUMERIC,
    CONSTRAINT order_details_pk PRIMARY KEY (id_order_detail),
    CONSTRAINT order_details_fk_1 FOREIGN KEY (id_order)
        REFERENCES sch_purchase_order.order_registration (id_order)
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE sch_purchase_order.order_details OWNER TO postgres;
```

---

### 📌 Tabla `payment_order`

```sql
CREATE TABLE IF NOT EXISTS sch_purchase_order.payment_order (
    id_payment_order INTEGER NOT NULL DEFAULT nextval('sch_purchase_order.payment_order_id_payment_order_seq'::regclass),
    id_order_registration INTEGER,
    names VARCHAR(50),
    surnames VARCHAR(50),
    email VARCHAR(100),
    phone VARCHAR(20),
    number_card VARCHAR(30),
    CONSTRAINT payment_order_pk PRIMARY KEY (id_payment_order),
    CONSTRAINT order_fk FOREIGN KEY (id_order_registration)
        REFERENCES sch_purchase_order.order_registration (id_order)
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE sch_purchase_order.payment_order OWNER TO postgres;
```

---

### 📌 Tabla `usuarios`

```sql
CREATE TABLE IF NOT EXISTS sch_purchase_order.usuarios (
    id INTEGER NOT NULL,
    username CHAR(50) NOT NULL,
    password CHAR(15) NOT NULL,
    CONSTRAINT usuarios_pkey PRIMARY KEY (id)
);

ALTER TABLE sch_purchase_order.usuarios OWNER TO postgres;
```
