package ua.nure.pharmacy.database;

public abstract class SQLQuery {
    public static class UserRepository{
        public static final String INSERT_USER = "INSERT INTO User " +
                "(login, password, role_id, name, address, phone)" +
                "VALUES (? , ? , 3, ? , ?, ?)";
        public static final String DELETE_USER = "DELETE FROM User WHERE id = ?";
    }

    public static class CustomerRepositorySQL {
        public static final String FIND_BY_ID_CUSTOMER = "SELECT * FROM Customer INNER JOIN User ON Customer.id = User.id WHERE Customer.id = ?";
        public static final String SELECT_ALL_CUSTOMER = "SELECT Customer.*, User.login, User.password, User.role_id, User.name, User.address, User.register_date, User.phone " +
                "FROM Customer INNER JOIN User ON Customer.id = User.id";
        public static final String INSERT_CUSTOMER =
                "INSERT INTO Customer (id, age, email)" +
                "VALUES ((SELECT MAX(id) FROM USER), ?, ?)";
        public static final String UPDATE_CUSTOMER_BY_ID = "UPDATE Customer INNER JOIN User ON Customer.id = User.id " +
                "SET login = ?, name = ?, address = ?, age = ?, email = ?, phone = ? " +
                "WHERE Customer.id = ?";
        public static final String DELETE_CUSTOMER = "DELETE Customer.*, User.* FROM Customer INNER JOIN User ON Customer.id = User.id WHERE Customer.id = ?";

    }


    public static class CustomerServiceSQL {
        public static final String FIND_BY_CUSTOMER_LOGIN = "SELECT Customer.*, " +
                "User.* FROM Customer INNER JOIN User ON Customer.id = User.id " +
                "WHERE User.login = ?";
    }

    public static class WorkerRepositorySQL {
        public static final String FIND_BY_ID_WORKER = "SELECT * FROM Worker INNER JOIN User ON Worker.id = User.id WHERE Worker.id = ?";
        public static final String SELECT_ALL_WORKER = "SELECT Worker.*, User.login, User.password, User.role_id, User.name, User.address, User.register_date, User.phone " +
                "FROM Worker INNER JOIN User ON Worker.id = User.id";
        public static final String INSERT_WORKER = "INSERT INTO Worker (id, start_date, end_date, position, login, password, role_id, name, address, register_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "INNER JOIN User ON Worker.id = User.id";
        public static final String UPDATE_WORKER_BY_ID = "UPDATE Worker INNER JOIN User ON Worker.id = User.id SET start_date = ?, end_date = ?, position = ?, login = ?, password = ?, role_id = ?, name = ?, address = ?, register_date = ? WHERE Worker.id = ?";
        public static final String DELETE_WORKER = "DELETE Worker.*, User.* FROM Worker INNER JOIN User ON Worker.id = User.id WHERE Worker.id = ?";

    }
    public static class WorkerServiceSQL {
        public static final String FIND_BY_WORKER_LOGIN = "SELECT Worker.*, User.* FROM Worker INNER JOIN User ON Worker.id = User.id WHERE Worker.login = ?";
    }
    public static class ManufacturerRepositorySQL{
        public static final String FIND_BY_ID_MANUFACTURER = "SELECT * FROM Manufacturer " +
                "WHERE id = ?";
        public static final String SELECT_ALL_MANUFACTURER = "SELECT * FROM Manufacturer";
        public static final String INSERT_MANUFACTURER = "INSERT INTO Manufacturer (id, name, country) " +
                "VALUES (?, ?, ?) ";
        public static final String UPDATE_MANUFACTURER_BY_ID = "UPDATE Manufacturer SET name = ?, " +
                "country = ? WHERE Manufacturer.id = ?";
        public static final String DELETE_MANUFACTURER = "DELETE FROM Manufacturer WHERE Manufacturer.id = ?";

        public static final String FIND_BY_NAME_MANUFACTURER = "SELECT * FROM Manufacturer " +
                "WHERE UPPER(name) LIKE ?";
    }

    public static class ProductRepositorySQL{
        public static final String FIND_BY_ID_PRODUCT = "SELECT * FROM Product WHERE id = ?";
        public static final String SELECT_ALL_PRODUCT = "SELECT * FROM Product";
        public static final String INSERT_PRODUCT = "INSERT INTO Product (id, name, description, manufacturer_id, photo_url) VALUES (?, ?, ?, ?, ?) ";
        public static final String UPDATE_PRODUCT = "UPDATE Product SET name = ?, description = ?," +
                "manufacturer_id = ? , photo_url = ? WHERE id = ?";
        public static final String DELETE_PRODUCT = "DELETE FROM Product WHERE id = ?";
        public static final String SELECT_ALL_PRODUCTS_BY_NAME =
                "SELECT * FROM Product WHERE name LIKE ?";
        public static final String SELECT_ALL_PRODUCTS_BY_MANUFACTURER_ID =
                "SELECT * FROM Product WHERE manufacturer_id = ?";
        public static final String ORDER_BY_NAME_ASC =
                "SELECT * FROM Product ORDER BY name ASC";
        public static final String ORDER_BY_NAME_DESC =
                "SELECT * FROM Product ORDER BY name DESC";
        public static final String FILTER_ALL_BY_PACK_AMOUNT =
                "SELECT product.description, product.name, product.id, product.photo_url, product.manufacturer_id  FROM pack " +
                        "INNER JOIN product ON pack.product_id = product.id " +
                        "WHERE pack.amount BETWEEN ? AND ?";
        public static final String FILTER_ALL_BY_DOSE_AMOUNT =
                "SELECT product.description, product.name, product.id, product.photo_url, product.manufacturer_id FROM dose INNER JOIN pack ON dose.id = pack.dose_id " +
                        "INNER JOIN product ON pack.product_id = product.id " +
                        "WHERE dose.amount BETWEEN ? AND ?";

        public static final String FILTER_ALL_BY_PACK_AND_DOSE_AMOUNT =
                "SELECT product.description, product.name, product.id, product.photo_url, product.manufacturer_id " +
                        "FROM pack " +
                        "INNER JOIN product ON pack.product_id = product.id " +
                        "INNER JOIN dose ON pack.dose_id = dose.id " +
                        "WHERE pack.amount BETWEEN ? AND ? " +
                        "AND dose.amount BETWEEN ? AND ? ";
        public static final String SELECT_ALL_PRODUCT_BY_CATEGORY_NAME =
                "SELECT * FROM product AS p JOIN product_category AS pc ON p.id = pc.product_id " +
                        "JOIN category AS c ON pc.category_id = c.id WHERE c.name = ? ";
        public static final String FIND_LAST = "SELECT * FROM product ORDER BY id DESC LIMIT 1";
        public static final String INSERT_PRODUCT_WITHOUT_ID = "INSERT INTO Product (name, description, manufacturer_id, photo_url) VALUES (?, ?, ?, ?)";
    }

    public static class CategoryRepositorySQL{
        public static final String FIND_BY_ID_CATEGORY = "SELECT * FROM Category WHERE id = ?";
        public static final String SELECT_ALL_CATEGORY = "SELECT * FROM Category";
        public static final String INSERT_CATEGORY = "INSERT INTO Category (id, name, parent_id) VALUES (?, ?, ?) ";
        public static final String UPDATE_CATEGORY= "UPDATE Category SET name = ?, parent_id = ? WHERE id = ?";
        public static final String DELETE_CATEGORY = "DELETE FROM Category WHERE id = ?";

    }

    public static class CartRepositorySQL{
        public static final String FIND_BY_ID_CART = "SELECT * FROM Cart WHERE id = ?";
        public static final String SELECT_ALL_CART = "SELECT * FROM Cart";
        public static final String INSERT_CART = "INSERT INTO Cart (customer_id, price) VALUES (?, ?) ";
        public static final String INSERT_CART_ONLY_CUSTOMER_iD = "INSERT INTO Cart (customer_id) VALUES (?) ";
        public static final String UPDATE_CART= "UPDATE Cart SET customer_id = ?, " +
                "price = ? WHERE id = ?";
        public static final String DELETE_CART = "DELETE FROM Cart WHERE id = ?";

        public static final String FIND_BY_CUSTOMER_ID = "SELECT * FROM Cart WHERE customer_id = ?";
    }


    public static class ProductCategoryRepositorySQL{
        public static final String FIND_BY_ID_PRODUCT_ID = "SELECT category.* FROM product_category " +
                "INNER JOIN category " +
                "ON product_category.category_id = category.id " +
                "WHERE product_id = ?";
        public static final String FIND_BY_ID_CATEGORY_ID = "SELECT * FROM product_category WHERE category_id = ?";
        public static final String SELECT_ALL_PRODUCT_CATEGORY = "SELECT * FROM product_category";
        public static final String INSERT_PRODUCT_CATEGORY = "INSERT INTO product_category (product_id, category_id) VALUES (?, ?) ";
        public static final String UPDATE_PRODUCT_CATEGORY = "UPDATE product_category SET product_id = ?, category_id = ? WHERE product_id = ? AND category_id = ?";
        public static final String DELETE_PRODUCT_CATEGORY = "DELETE FROM product_category WHERE product_id = ? AND category_id = ?";
    }

    public static class CartPackRepositorySQL {
        public static final String FIND_BY_ID_CART_ID = "SELECT * FROM cart_pack WHERE cart_id = ?";
        public static final String SELECT_ALL_CART_PACK = "SELECT * FROM cart_pack";
        public static final String INSERT_CART_PACK = "INSERT INTO cart_pack " +
                "(pack_id, cart_id, amount) VALUES (?, ?, ?) ";

        public static final String UPDATE_CART_PACK = "UPDATE cart_pack " +
                "SET amount = ? WHERE pack_id = ? AND cart_id = ?";

        public static final String DELETE_CART_PACK = "DELETE FROM cart_pack " +
                "WHERE pack_id = ? AND cart_id = ?";

        public static final String FIND_BY_ID_CART_AND_PACK_ID =
                "SELECT * FROM cart_pack WHERE pack_id = ? AND cart_id = ?";
    }

    public static class RoleRepositorySQL{
        public static final String FIND_BY_ID_ROLE = "SELECT * FROM Role WHERE Role.id = ?";
        public static final String SELECT_ALL_ROLE = "SELECT * FROM Role";
        public static final String INSERT_ROLE = "INSERT INTO Role (id, name) " +
                "VALUES (?, ?) ";
        public static final String UPDATE_ROLE_BY_ID = "UPDATE Role SET name = ? " +
                "WHERE Role.id = ?";
        public static final String DELETE_ROLE = "DELETE FROM Role WHERE Role.id = ?";

    }

    public static class DoseRepositorySQL{
        public static final String FIND_BY_ID_DOSE = "SELECT * FROM Dose WHERE Dose.id = ?";
        public static final String SELECT_ALL_DOSE = "SELECT * FROM Dose";
        public static final String INSERT_DOSE = "INSERT INTO Dose (id, amount, measure) " +
                "VALUES (?, ?, ?) ";
        public static final String UPDATE_DOSE_BY_ID = "UPDATE Dose SET amount = ?, measure = ? " +
                "WHERE Dose.id = ?";
        public static final String DELETE_DOSE = "DELETE FROM Dose WHERE Dose.id = ?";

    }

    public static class PackRepositorySQL{
        public static final String FIND_BY_ID_PACK = "SELECT * FROM Pack WHERE Pack.id = ?";
        public static final String SELECT_ALL_PACK = "SELECT * FROM Pack";
        public static final String INSERT_PACK = "INSERT INTO Pack (id, amount, price, product_id, " +
                "expiration_date, manufacture_date, dose_id, packs_amount) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
        public static final String UPDATE_PACK_BY_ID = "UPDATE Pack SET amount = ?, " +
                "price = ?, product_id = ?, expiration_date = ?, manufacture_date = ?, dose_id = ?, " +
                "packs_amount = ? WHERE Pack.id = ?";
        public static final String DELETE_PACK = "DELETE FROM Pack WHERE Pack.id = ?";
        public static final String FIND_BY_PRODUCT_ID_PACK = "SELECT * FROM Pack WHERE Pack.product_id = ?";

        public static final String INSERT_PACK_WITHOUT_ID = "INSERT INTO Pack (amount, price, product_id, " +
                "expiration_date, manufacture_date, dose_id, packs_amount) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?) ";
    }

    public static class PackOrderRepositorySQL {
        public static final String FIND_BY_ID_ORDER_ID = "SELECT * FROM pack_order WHERE order_id = ?";
        public static final String SELECT_ALL_PACK_ORDER = "SELECT * FROM pack_order";
        public static final String INSERT_PACK_ORDER = "INSERT INTO pack_order " +
                "(pack_id, order_id, amount, price) VALUES (?, ?, ?, ?) ";

        public static final String UPDATE_PACK_ORDER = "UPDATE pack_order " +
                "SET amount = ? SET price = ? WHERE pack_id = ? AND order_id = ?";

        public static final String DELETE_PACK_ORDER = "DELETE FROM pack_order " +
                "WHERE pack_id = ? AND order_id = ?";
    }

    public static class OrderRepositorySQL{
        public static final String FIND_BY_ID_ORDER = "SELECT * FROM `Order` WHERE id = ?";

        public static final String INSERT_ORDER =
                "INSERT INTO `Order` (customer_id, status_id, emergency_id, price) " +
                "VALUES (?, ?, ?, ?)";

        public static final String UPDATE_ORDER_BY_ID =
                "UPDATE `Order` SET customer_id = ?, status_id = ?, emergency_id = ?, price = ? " +
                "WHERE id = ?";

        public static final String SELECT_ALL_ORDER = "SELECT * FROM `Order`";

        public static final String DELETE_ORDER = "DELETE FROM `Order` WHERE id = ?";

        public static final String FIND_LAST = "SELECT * FROM `order` ORDER BY `id` DESC LIMIT 1";

        public static final String FIND_BY_ID_ORDER_CUSTOMER_ID = "SELECT * FROM `Order` WHERE customer_id = ?";
        public static final String FIND_BY_ID_ORDER_STATUS_ID = "SELECT * FROM `Order` WHERE status_id = ?";;
        public static final String FIND_BY_STATUS_ID_ORDER_BY_EMERGENCY = "SELECT * FROM `order` WHERE status_id = ? ORDER BY emergency_id";
    }
    public static class ContactRepositorySQL{
        public static final String FIND_BY_ID_CONTACT = "SELECT * FROM Contact WHERE id = ?";
        public static final String FIND_BY_ORDER_ID_CONTACT = "SELECT * FROM Contact WHERE order_id = ?";

        public static final String INSERT_CONTACT =
                "INSERT INTO Contact (order_id, name, email, phone) " +
                        "VALUES (?, ?, ?, ?)";

        public static final String UPDATE_CONTACT_BY_ID =
                "UPDATE Contact SET order_id = ?, name = ?, email = ?, phone = ? " +
                        "WHERE id = ?";

        public static final String SELECT_ALL_CONTACT = "SELECT * FROM Contact";

        public static final String DELETE_CONTACT = "DELETE FROM Contact WHERE id= ?";

    }

    public static class OrderStatusRepositorySQL{
        public static final String FIND_BY_ID_ORDER_STATUS = "SELECT * FROM Order_Status WHERE Order_Status.id = ?";

        public static final String INSERT_ORDER_STATUS = "INSERT INTO Order_Status (name) VALUES (?)";

        public static final String UPDATE_ORDER_STATUS_BY_ID = "UPDATE Order_Status SET name = ? WHERE id = ?";

        public static final String SELECT_ALL_ORDER_STATUS= "SELECT * FROM Order_Status";

        public static final String DELETE_ORDER_STATUS = "DELETE FROM Order_Status WHERE Order_Status.id = ?";

    }

    public static class EmergencyRepositorySQL{
        public static final String FIND_BY_ID_EMERGENCY = "SELECT * FROM Emergency WHERE Emergency.id = ?";
        public static final String INSERT_EMERGENCY = "INSERT INTO Emergency (name) VALUES (?)";
        public static final String UPDATE_EMERGENCY_BY_ID = "UPDATE Emergency SET name = ? WHERE id = ?";
        public static final String SELECT_ALL_EMERGENCY = "SELECT * FROM Emergency";
        public static final String DELETE_EMERGENCY = "DELETE FROM Emergency WHERE Emergency.id = ?";
    }

    public static class OrderHistoryRepositorySQL{
        public static final String FIND_BY_ID_ORDER_HISTORY = "SELECT * FROM Order_History WHERE id = ?";
        public static final String FIND_BY_ID_ORDER_ID_ORDER_HISTORY = "SELECT * FROM Order_History WHERE order_id = ?";
        public static final String INSERT_ORDER_HISTORY = "INSERT INTO Order_History (date, order_id, worker_id, status_id) VALUES (?, ?, ?, ?)";
        public static final String INSERT_ORDER_HISTORY_WITHOUT_WORKER = "INSERT INTO Order_History (date, order_id, status_id) VALUES (?, ?, ?)";
        public static final String UPDATE_ORDER_HISTORY_BY_ID = "UPDATE Order_History SET date = ?, SET order_id = ?, SET worker_id = ?, SET status_id = ? WHERE id = ?";
        public static final String SELECT_ALL_ORDER_HISTORY = "SELECT * FROM Order_History";
        public static final String DELETE_ORDER_HISTORY = "DELETE FROM Order_History WHERE id = ?";
    }

    public static class Task{
        public static final String TASK = "SELECT u.name, SUM(p_o.amount) AS products_amount, SUM(o.price) AS total_sum FROM user u INNER JOIN customer c ON u.id = c.id INNER JOIN `order` o ON o.customer_id = c.id INNER JOIN pack_order p_o ON p_o.order_id = o.id INNER JOIN order_history o_h ON o_h.order_id = o.id WHERE o_h.date >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH) GROUP BY u.id ORDER BY total_sum DESC";
    }
}