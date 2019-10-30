# GameStore-Project

This project involves creating a simple database backend REST inventory management web service for a Video Game Store using Agile development techniques in a solo setting. Responsibilities include designing and documenting the REST API and implementing the controller, service, layer, DAO, Java data objects, and unit tests for the application based on an existing database structure. Additionally, Spring Security has been applied.

## Features

* REST API allows the end user to:
1. Games:
   1. Perform standard CRUD operations for Games
   1. Search for Games by Studio
   1. Search for Games by ESRB Rating
   1. Search for Games by Title
1. Consoles:
   1. Perform standard CRUD operations for Consoles
   1. Search for Consoles by Manufacturer
1. T-Shirts:
   1. Perform standard CRUD operations for T-Shirts
   1. Search for T-Shirts by Color
   1. Search for T-Shirts by Size
1. Purchasing Items:
   1. User can purchase items in inventory by supplying the following information to the endpoint:
       1. Name
       1. Street
       1. City
       1. State
       1. Zip
       1. Item Type
       1. Item ID
       1. Quantity
   1. The endpoint returns invoice data based on the invoice table below.
   1. All invoice calculations completed in the Service Layer.
   1. Seperate a DAO for both taxes and processing fees.
   
   Based on following database structure:

   ```sql
   create schema if not exists game_store;
   use game_store;

   create table if not exists game (
       game_id int(11) not null auto_increment primary key,
       title varchar(50) not null,
       esrb_rating varchar(50) not null,
       description varchar(255) not null,
       price decimal(5, 2) not null,
       studio varchar(50) not null,
       quantity int(11)
   );

   create table if not exists console (
       console_id int(11) not null auto_increment primary key,
       model varchar(50) not null,
       manufacturer varchar(50) not null,
       memory_amount varchar(20),
       processor varchar(20),
       price decimal(5, 2) not null,
       quantity int(11) not null
   );

   create table if not exists t_shirt (
       t_shirt_id int(11) not null auto_increment primary key,
       size varchar(20) not null,
       color varchar(20) not null,
       description varchar(255) not null,
       price decimal(5,2) not null,
       quantity int(11) not null
   );

   create table if not exists sales_tax_rate (
       state char(2) not null,
       rate decimal(3,2) not null
   );

   create unique index ix_state_rate on sales_tax_rate (state, rate);

   create table if not exists processing_fee (
       product_type varchar(20) not null,
       fee decimal (4,2)
   );

   create unique index ix_product_type_fee on processing_fee (product_type, fee);

   create table if not exists invoice (
       invoice_id int(11) not null auto_increment primary key,
       name varchar(80) not null,
       street varchar(30) not null,
       city varchar(30) not null,
       state varchar(30) not null,
       zipcode varchar(5) not null,
       item_type varchar(20) not null,
       item_id int(11) not null,
       unit_price decimal(5,2) not null,
       quantity int(11) not null,
       subtotal decimal(5,2) not null,
       tax decimal(5,2) not null,
       processing_fee decimal (5,2) not null,
       total decimal(5,2) not null
   );
   
   create table if not exists users(
       username varchar(50) not null primary key,
       password varchar(100) not null,
       enabled boolean not null
   );

   create table if not exists authorities (
       username varchar(50) not null,
       authority varchar(50) not null,
       constraint fk_authorities_users foreign key(username) references users(username));
       create unique index ix_auth_username on authorities (username,authority
   );

   insert into users (username, password, enabled) values ('staff', '$2a$10$MjQyv.dx4EzbYtalp2Ff8O/R50clejbGSeWlaDloyEd3RjICubmcq', true);
   insert into users (username, password, enabled) values ('manager', '$2a$10$t6s91GZyRGb9bav05Q1HSOcBGH0.sHDGOmtc2zm4z32ojWc6q0Vum', true);
   insert into users (username, password, enabled) values ('admin', '$2a$10$cKuY9hZblmOkRg8MTBWkZudJ7SL6JBPSufN66xro78PHe17H8bFbm', true);

   insert into authorities (username, authority) values ('staff', 'ROLE_STAFF');
   insert into authorities (username, authority) values ('manager', 'ROLE_STAFF');
   insert into authorities (username, authority) values ('manager', 'ROLE_MANAGER');
   insert into authorities (username, authority) values ('admin', 'ROLE_STAFF');
   insert into authorities (username, authority) values ('admin', 'ROLE_MANAGER');
   insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');
   ```
   ## Tests

   1. Routes tested using MockMVC.
       - This included testing for both expected return values, and expected controller failures (4xx and 5xx status codes)
   1. Tested all service layer methods
       - 100% code coverage of the service layer
       - Service layer tests are unit tests
   1. integration tests for all DAOs
       - Tested the basic CRUD operations
       - Including custom methods defined (such as findByCategory)
       
       ## Business Rules

       1. Sales tax applies only to the cost of the items.
       2. Sales tax does not apply to any processing fees for an invoice.
       3. The processing fee is applied only once per order regardless of the number of items in the order unless the number of items on the order is greater than 10 in which case an *additional* processing fee of $15.49 is applied to the order. 
       4. The order process logic must properly update the quantity on hand for the item in the order.
       5. Order quantity must be greater than zero.
       6. Order quantity must be less than or equal to the number of items on hand in inventory.
       7. Order must contain a valid state code.
       8. The REST API must properly handle and report all violations of business rules.
       
       ## Security Rules

       Following security rules apply:

       ``` Spring Security ```

       * Searching:
         * Any user (both authenticated and unauthenticated) can search for products.
       * Updates:
         * Any staff member can update a product.
       * Create:
         * Only Managers and above can create new products.
       * Delete
         * Only Admin users can delete a product.
       
       ## Data

       ### Tax Rates

       Load the following tax rates into your database:

       - Alabama - AL: .05
       - Alaska - AK: .06
       - Arizona - AZ: .04
       - Arkansas - AR: .06
       - California - CA: .06
       - Colorado - CO: .04
       - Connecticut - CT: .03
       - Delaware - DE: .05
       - Florida - FL: .06
       - Georgia - GA: .07
       - Hawaii - HI: .05
       - Idaho - ID: .03
       - Illinois - IL: .05
       - Indiana - IN: .05
       - Iowa - IA: .04
       - Kansas - KS: .06
       - Kentucky - KY: .04
       - Louisiana - LA: .05
       - Maine - ME: .03
       - Maryland - MD: .07
       - Massachusetts - MA: .05
       - Michigan - MI: .06
       - Minnesota - MN: .06
       - Mississippi - MS: .05
       - Missouri - MO: .05
       - Montana - MT: .03
       - Nebraska - NE: .04
       - Nevada - NV: .04
       - New Hampshire - NH: .06
       - New Jersey - NJ: .05
       - New Mexico - NM: .05
       - New York - NY: .06
       - North Carolina - NC: .05
       - North Dakota - ND: .05
       - Ohio - OH: .04
       - Oklahoma - OK: .04
       - Oregon - OR: .07
       - Pennsylvania - PA: .06
       - Rhode Island - RI: .06
       - South Carolina - SC: .06
       - South Dakota - SD: .06
       - Tennessee - TN: .05
       - Texas - TX: .03
       - Utah - UT: .04
       - Vermont - VT: .07
       - Virginia - VA: .06
       - Washington - WA: .05
       - West Virginia - WV: .05
       - Wisconsin - WI: .03
       - Wyoming - WY: .04

       ### Processing Fees

       Load the following processing fees into your database:

       * Consoles: 14.99
       * T-Shirts: 1.98
       * Games: 1.49
       
       ---
