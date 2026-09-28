package com.example.campusdash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CampusDash.db";
    private static final int DATABASE_VERSION = 6;

    // Table Names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_ORDERS = "orders";
    public static final String TABLE_PAYMENTS = "payments";
    public static final String TABLE_STORES = "stores";
    public static final String TABLE_MEALS = "meals";
    public static final String TABLE_CART = "cart";
    public static final String TABLE_DELIVERY_GUYS = "delivery_guys";
    public static final String TABLE_ORDER_ITEMS = "order_items";

    // User Column Names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_FULLNAME = "fullname";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_PHONE = "phone";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EMAIL + " TEXT UNIQUE, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_FULLNAME + " TEXT, "
                + COLUMN_ROLE + " TEXT, "
                + COLUMN_PHONE + " TEXT" + ")";

        String CREATE_STORES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_STORES + " ("
                + "storeid INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "storename TEXT, "
                + "vendorname TEXT, "
                + "vendorid INTEGER, "
                + "storeimage TEXT, "
                + "rating TEXT" + ")";

        String CREATE_MEALS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_MEALS + " ("
                + "mealid INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "mealname TEXT, "
                + "meaimage TEXT, "
                + "mealprice REAL, "
                + "storeid INTEGER, "
                + "mealcatergory TEXT" + ")";

        String CREATE_ORDERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ORDERS + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "order_number TEXT, "
                + "student_name TEXT, "
                + "vendor_name TEXT, "
                + "amount REAL, "
                + "status TEXT" + ")";

        String CREATE_PAYMENTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PAYMENTS + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "payment_number TEXT, "
                + "student_name TEXT, "
                + "vendor_name TEXT, "
                + "amount REAL, "
                + "method TEXT, "
                + "status TEXT" + ")";

        String CREATE_DELIVERY_GUYS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DELIVERY_GUYS + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "deliveryguyname TEXT, "
                + "email TEXT" + ")";

        String CREATE_CART_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CART + " ("
                + "cartid INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "mealid INTEGER, "
                + "mealname TEXT, "
                + "mealprice REAL, "
                + "meaimage TEXT, "
                + "quantity INTEGER" + ")";

        String CREATE_ORDER_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ORDER_ITEMS + " ("
                + "item_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "order_id INTEGER, "
                + "meal_name TEXT, "
                + "meal_price REAL, "
                + "quantity INTEGER" + ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_STORES_TABLE);
        db.execSQL(CREATE_MEALS_TABLE);
        db.execSQL(CREATE_ORDERS_TABLE);
        db.execSQL(CREATE_PAYMENTS_TABLE);
        db.execSQL(CREATE_DELIVERY_GUYS_TABLE);
        db.execSQL(CREATE_CART_TABLE);
        db.execSQL(CREATE_ORDER_ITEMS_TABLE);

        seedData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DELIVERY_GUYS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_ITEMS);
        onCreate(db);
    }

    private void seedData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_STORES + " (storename, vendorname, vendorid, storeimage, rating) VALUES ('Campus Grill', 'John Vendor', 1, 'https://images.unsplash.com/photo-1555396273-367ea4eb4db5', '4.8')");
        db.execSQL("INSERT INTO " + TABLE_STORES + " (storename, vendorname, vendorid, storeimage, rating) VALUES ('Fast Eats', 'Mary Vendor', 2, 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4', '4.5')");

        db.execSQL("INSERT INTO " + TABLE_MEALS + " (mealname, meaimage, mealprice, storeid, mealcatergory) VALUES "
                + "('Cheeseburger & Fries', 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd', 65.00, 1, 'Fast Food'), "
                + "('Pepperoni Pizza Slice', 'https://images.unsplash.com/photo-1513104890138-7c749659a591', 35.00, 1, 'Pizza'), "
                + "('Grilled Meat & Phuthu', 'https://images.unsplash.com/photo-1544025162-d76694265947', 55.00, 1, 'Grilled Meat'), "
                + "('Grilled Chicken Salad', 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c', 55.00, 2, 'Healthy'), "
                + "('Steamed Bread & Stew', 'https://images.unsplash.com/photo-1547592180-85f173990554', 45.00, 2, 'Traditional')");

        db.execSQL("INSERT INTO " + TABLE_ORDERS + " (order_number, student_name, vendor_name, amount, status) VALUES "
                + "('ORD-1001', 'Thabo Mokoena', 'Campus Grill', 100.00, 'Completed'), "
                + "('ORD-1002', 'Lindiwe Ndlovu', 'Fast Eats', 55.00, 'Pending'), "
                + "('ORD-1003', 'Sipho Dlamini', 'Campus Grill', 65.00, 'In Progress')");

        db.execSQL("INSERT INTO " + TABLE_PAYMENTS + " (payment_number, student_name, vendor_name, amount, method, status) VALUES "
                + "('PAY-5001', 'Thabo Mokoena', 'Campus Grill', 100.00, 'Card', 'Successful'), "
                + "('PAY-5002', 'Lindiwe Ndlovu', 'Fast Eats', 55.00, 'EFT', 'Pending'), "
                + "('PAY-5003', 'Sipho Dlamini', 'Campus Grill', 65.00, 'Cash', 'Successful')");
    }

    // --- SEED DUMMY ORDERS METHOD ---

    public void seedDummyOrdersIfEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ORDERS, null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            if (count == 0) {
                db.execSQL("INSERT INTO " + TABLE_ORDERS + " (order_number, student_name, vendor_name, amount, status) VALUES "
                        + "('ORD-1001', 'Thabo Mokoena', 'Campus Grill', 100.00, 'Completed'), "
                        + "('ORD-1002', 'Lindiwe Ndlovu', 'Fast Eats', 55.00, 'Pending'), "
                        + "('ORD-1003', 'Sipho Dlamini', 'Campus Grill', 65.00, 'In Progress')");
            }
        }
    }

    // --- USER MANAGEMENT ---

    public boolean insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, user.getUserEmail());
        values.put(COLUMN_PASSWORD, user.getUserHashedPassword());
        values.put(COLUMN_FULLNAME, user.getFullnames());
        values.put(COLUMN_ROLE, user.getRole() != null ? user.getRole() : "Member");
        values.put(COLUMN_PHONE, user.getPhonenumber() != null ? user.getPhonenumber() : "");

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUserExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID},
                COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);
        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        return exists;
    }

    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID},
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);
        boolean isValid = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        return isValid;
    }

    public User getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null,
                COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            String pass = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULLNAME));
            String role = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));

            user = new User(email, pass, name, role, phone);
        }
        if (cursor != null) cursor.close();
        return user;
    }

    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USERS, null, COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);
    }

    // --- DASHBOARD METRICS ---

    public int getVendorsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_USERS + " WHERE " + COLUMN_ROLE + " = 'Vendor'", null);
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        if (cursor != null) cursor.close();
        return count;
    }

    public int getActiveVendorsCount() {
        return getVendorsCount();
    }

    public int getOrdersCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ORDERS, null);
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        if (cursor != null) cursor.close();
        return count;
    }

    public int getPaymentsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_PAYMENTS, null);
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        if (cursor != null) cursor.close();
        return count;
    }

    // --- VENDOR & STORE METHODS ---

    public boolean addVendorAndStore(String vendorName, String email, String storeName, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            String defaultPassword = storeName.replaceAll("\\s+", "").toLowerCase() + "@123";

            ContentValues userValues = new ContentValues();
            userValues.put(COLUMN_EMAIL, email);
            userValues.put(COLUMN_PASSWORD, defaultPassword);
            userValues.put(COLUMN_FULLNAME, vendorName);
            userValues.put(COLUMN_ROLE, "Vendor");
            userValues.put(COLUMN_PHONE, phone);

            long userId = db.insert(TABLE_USERS, null, userValues);
            if (userId == -1) return false;

            ContentValues storeValues = new ContentValues();
            storeValues.put("storename", storeName);
            storeValues.put("vendorname", vendorName);
            storeValues.put("vendorid", userId);
            storeValues.put("storeimage", "");
            storeValues.put("rating", "5.0");

            long storeId = db.insert(TABLE_STORES, null, storeValues);
            if (storeId == -1) return false;

            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public Cursor getAllVendorsWithStores() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT u.id AS vendorid, u.fullname AS vendorName, u.email AS vendorEmail, "
                + "s.storeid, s.storename AS storeName "
                + "FROM " + TABLE_USERS + " u "
                + "LEFT JOIN " + TABLE_STORES + " s ON u.id = s.vendorid "
                + "WHERE u.role = 'Vendor'";
        return db.rawQuery(query, null);
    }

    public boolean deleteVendor(int vendorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_USERS, COLUMN_ID + "=?", new String[]{String.valueOf(vendorId)});
            db.delete(TABLE_STORES, "vendorid=?", new String[]{String.valueOf(vendorId)});
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
        }
    }

    // --- ORDERS & ORDER ITEMS METHODS ---

    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ORDERS, null, null, null, null, null, "id DESC");
    }

    public boolean insertOrder(String orderNumber, String studentName, String vendorName, double amount, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("order_number", orderNumber);
        values.put("student_name", studentName);
        values.put("vendor_name", vendorName);
        values.put("amount", amount);
        values.put("status", status);
        long result = db.insert(TABLE_ORDERS, null, values);
        return result != -1;
    }

    public boolean updateOrderStatus(int orderId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", newStatus);
        return db.update(TABLE_ORDERS, values, "id=?", new String[]{String.valueOf(orderId)}) > 0;
    }

    public boolean updateOrderStatus(String orderId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", newStatus);
        return db.update(TABLE_ORDERS, values, "id=?", new String[]{orderId}) > 0;
    }

    public Cursor getOrderItems(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ORDER_ITEMS, null, "order_id=?", new String[]{String.valueOf(orderId)}, null, null, null);
    }

    public boolean insertOrderItem(int orderId, String mealName, double mealPrice, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("order_id", orderId);
        values.put("meal_name", mealName);
        values.put("meal_price", mealPrice);
        values.put("quantity", quantity);
        long result = db.insert(TABLE_ORDER_ITEMS, null, values);
        return result != -1;
    }

    // --- PAYMENTS METHODS ---



    public boolean insertPayment(String paymentNumber, String studentName, String vendorName, double amount, String method, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("payment_number", paymentNumber);
        values.put("student_name", studentName);
        values.put("vendor_name", vendorName);
        values.put("amount", amount);
        values.put("method", method);
        values.put("status", status);
        long result = db.insert(TABLE_PAYMENTS, null, values);
        return result != -1;
    }

    // --- MEALS METHODS ---

    public boolean insertMeal(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mealname", meal.getMealname());
        values.put("meaimage", meal.getMeaimage());
        values.put("mealprice", meal.getMealprice());
        values.put("storeid", meal.getStoreid());
        values.put("mealcatergory", meal.getMealcatergory());

        long result = db.insert(TABLE_MEALS, null, values);
        return result != -1;
    }

    public boolean insertMeal(String name, double price, String category, String imageUrl, int storeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mealname", name);
        values.put("mealprice", price);
        values.put("mealcatergory", category);
        values.put("meaimage", imageUrl);
        values.put("storeid", storeId);
        return db.insert(TABLE_MEALS, null, values) != -1;
    }

    public boolean updateMeal(int mealId, String name, double price, String category, String imageUrl, int storeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mealname", name);
        values.put("mealprice", price);
        values.put("mealcatergory", category);
        values.put("meaimage", imageUrl);
        values.put("storeid", storeId);
        return db.update(TABLE_MEALS, values, "mealid=?", new String[]{String.valueOf(mealId)}) > 0;
    }

    public boolean deleteMeal(int mealId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_MEALS, "mealid=?", new String[]{String.valueOf(mealId)}) > 0;
    }

    public List<Meal> getAllMeals() {
        List<Meal> mealList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MEALS, null, null, null, null, null, "mealid DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("mealid"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("mealname"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("meaimage"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("mealprice"));
                int storeId = cursor.getInt(cursor.getColumnIndexOrThrow("storeid"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("mealcatergory"));

                mealList.add(new Meal(id, name, image, price, storeId, category));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return mealList;
    }

    public List<Meal> getMealsByStore(int targetStoreId) {
        List<Meal> mealList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MEALS, null, "storeid=?",
                new String[]{String.valueOf(targetStoreId)}, null, null, "mealid DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("mealid"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("mealname"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("meaimage"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("mealprice"));
                int storeId = cursor.getInt(cursor.getColumnIndexOrThrow("storeid"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("mealcatergory"));

                mealList.add(new Meal(id, name, image, price, storeId, category));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return mealList;
    }

    // --- CART METHODS ---

    public void addToCart(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_CART, new String[]{"cartid", "quantity"},
                "mealid=?", new String[]{String.valueOf(meal.getMealid())}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int cartId = cursor.getInt(cursor.getColumnIndexOrThrow("cartid"));
            int currentQty = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
            cursor.close();

            ContentValues values = new ContentValues();
            values.put("quantity", currentQty + 1);
            db.update(TABLE_CART, values, "cartid=?", new String[]{String.valueOf(cartId)});
        } else {
            if (cursor != null) cursor.close();
            ContentValues values = new ContentValues();
            values.put("mealid", meal.getMealid());
            values.put("mealname", meal.getMealname());
            values.put("mealprice", meal.getMealprice());
            values.put("meaimage", meal.getMeaimage());
            values.put("quantity", 1);
            db.insert(TABLE_CART, null, values);
        }
    }

    public Cursor getCartItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_CART, null, null, null, null, null, null);
    }

    public void updateCartQuantity(int cartId, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (newQuantity <= 0) {
            db.delete(TABLE_CART, "cartid=?", new String[]{String.valueOf(cartId)});
        } else {
            ContentValues values = new ContentValues();
            values.put("quantity", newQuantity);
            db.update(TABLE_CART, values, "cartid=?", new String[]{String.valueOf(cartId)});
        }
    }

    public void clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, null, null);
    }

    // --- DELIVERY GUY OPERATIONS ---

    public boolean insertDeliveryGuy(String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("deliveryguyname", name);
        values.put("email", email);
        long result = db.insert(TABLE_DELIVERY_GUYS, null, values);
        return result != -1;
    }

    public List<DeliveryGuy> getAllDeliveryGuys() {
        List<DeliveryGuy> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DELIVERY_GUYS, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("deliveryguyname"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                list.add(new DeliveryGuy(id, name, email));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public boolean deleteDeliveryGuy(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_DELIVERY_GUYS, "id=?", new String[]{String.valueOf(id)}) > 0;
    }

// --- PAYMENTS SEED & FETCH METHODS ---

    /**
     * Seeds initial mock/dummy payment records into the database if the payments table is empty.
     */
    public void seedDummyPaymentsIfEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if table already contains data
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_PAYMENTS, null);
        int count = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }

        // Seed dummy records using your schema's column structure
        if (count == 0) {
            db.beginTransaction();
            try {
                insertDummyPayment(db, "PAY-5001", "Thabo Mokoena", "Campus Grill", 100.00, "Card", "Successful");
                insertDummyPayment(db, "PAY-5002", "Lindiwe Ndlovu", "Fast Eats", 55.00, "EFT", "Pending");
                insertDummyPayment(db, "PAY-5003", "Sipho Dlamini", "Campus Grill", 65.00, "Cash", "Successful");
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }

    private void insertDummyPayment(SQLiteDatabase db, String paymentNumber, String studentName, String vendorName, double amount, String method, String status) {
        ContentValues values = new ContentValues();
        values.put("payment_number", paymentNumber);
        values.put("student_name", studentName);
        values.put("vendor_name", vendorName);
        values.put("amount", amount);
        values.put("method", method);
        values.put("status", status);
        db.insert(TABLE_PAYMENTS, null, values);
    }

    /**
     * Retrieves all payment records from the database as a List<Payment>.
     */
    public List<Payment> getAllPaymentsList() {
        List<Payment> paymentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_PAYMENTS + " ORDER BY id DESC";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String paymentNumber = cursor.getString(cursor.getColumnIndexOrThrow("payment_number"));
                String studentName = cursor.getString(cursor.getColumnIndexOrThrow("student_name"));
                String vendorName = cursor.getString(cursor.getColumnIndexOrThrow("vendor_name"));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));
                String method = cursor.getString(cursor.getColumnIndexOrThrow("method"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));

                // Ensure your Payment model constructor matches this parameter order:
                // Payment(id, paymentNumber, studentName, vendorName, amount, method, status)
                Payment payment = new Payment(id, paymentNumber, studentName, vendorName, amount, method, status);
                paymentList.add(payment);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return paymentList;
    }
}