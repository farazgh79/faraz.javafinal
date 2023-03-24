import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Product {
    String name;
    String category;
    double price;
    String description;
    Product(String name,String category, double price,String description){
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    String getName(){
        return this.name;
    }
    String getCategory(){
        return this.category;
    }
    String getDescription(){
        return this.description;
    }
    double getPrice(){
        return this.price;
    }
    void setName(String name){
        this.name = name;
    }
    void setCategory(String category){
        this.category = category;
    }
    void setDescription(String description){
        this.description = description;
    }
    void setPrice(double price){
        this.price = price;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, List<Product>> productsByCategory = new HashMap<>();

        while (true) {
            System.out.println("Welcome to the retail store application!");
            System.out.println("Please select an option:");
            System.out.println("1. Add product");
            System.out.println("2. Delete product");
            System.out.println("3. Edit product");
            System.out.println("4. List products");
            System.out.println("5. Search products");
            System.out.println("6. Exit");

            int option = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (option) {
                case 1:
                    addProduct(scanner, productsByCategory);
                    break;
                case 2:
                    deleteProduct(scanner, productsByCategory);
                    break;
                case 3:
                    editProduct(scanner, productsByCategory);
                    break;
                case 4:
                    listProducts(productsByCategory);
                    break;
                case 5:
                    searchProducts(scanner, productsByCategory);
                    break;
                case 6:
                    System.out.println("Thank you for using the retail store application!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void addProduct(Scanner scanner, Map<String, List<Product>> productsByCategory) {
        System.out.println("Please enter the product name:");
        String name = scanner.nextLine();

        System.out.println("Please enter the product category:");
        String category = scanner.nextLine();

        System.out.println("Please enter the product price:");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume the newline character

        System.out.println("Please enter the product description:");
        String description = scanner.nextLine();

        Product product = new Product(name, category, price, description);

        List<Product> products = productsByCategory.getOrDefault(category, new ArrayList<>());
        products.add(product);
        productsByCategory.put(category, products);

        System.out.println("Product added successfully.");
    }

    private static void deleteProduct(Scanner scanner, Map<String, List<Product>> productsByCategory) {
        System.out.println("Please enter the product name:");
        String name = scanner.nextLine();

        System.out.println("Please enter the product category:");
        String category = scanner.nextLine();

        List<Product> products = productsByCategory.get(category);

        if (products != null) {
            Product productToRemove = null;

            for (Product product : products) {
                if (product.getName().equals(name)) {
                    productToRemove = product;
                    break;
                }
            }

            if (productToRemove != null) {
                products.remove(productToRemove);

                if (products.isEmpty()) {
                    productsByCategory.remove(category);
                }

                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Product not found.");
            }
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void editProduct(Scanner scanner, Map<String, List<Product>> productsByCategory) {
        System.out.println("Please enter the product name:");
        String name = scanner.nextLine();

        System.out.println("Please enter the product category:");
        String category = scanner.nextLine();

        List<Product> products = productsByCategory.get(category);

        if (products != null) {
            Product productToEdit = null;

            for (Product product : products) {
                if (product.getName().equals(name)) {
                    productToEdit = product;
                    break;
                }
            }

            if (productToEdit != null) {
                System.out.println("Please select a property to edit:");
                System.out.println("1. Name");
                System.out.println("2. Category");
                System.out.println("3. Price");
                System.out.println("4. Description");

                int propertyToEdit = scanner.nextInt();
                scanner.nextLine(); // consume the newline character

                switch (propertyToEdit) {
                    case 1:
                        System.out.println("Please enter the new name:");
                        String newName = scanner.nextLine();
                        productToEdit.setName(newName);
                        break;
                    case 2:
                        System.out.println("Please enter the new category:");
                        String newCategory = scanner.nextLine();

                        // Remove the product from the old category list
                        products.remove(productToEdit);
                        if (products.isEmpty()) {
                            productsByCategory.remove(category);
                        }

                        // Add the product to the new category list
                        List<Product> newCategoryProducts = productsByCategory.getOrDefault(newCategory, new ArrayList<>());
                        newCategoryProducts.add(productToEdit);
                        productsByCategory.put(newCategory, newCategoryProducts);
                        productToEdit.setCategory(newCategory);
                        break;
                    case 3:
                        System.out.println("Please enter the new price:");
                        double newPrice = scanner.nextDouble();
                        scanner.nextLine(); // consume the newline character
                        productToEdit.setPrice(newPrice);
                        break;
                    case 4:
                        System.out.println("Please enter the new description:");
                        String newDescription = scanner.nextLine();
                        productToEdit.setDescription(newDescription);
                        break;
                    default:
                        System.out.println("Invalid option. No property edited.");
                        break;
                }

                System.out.println("Product edited successfully.");
            } else {
                System.out.println("Product not found.");
            }
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void listProducts(Map<String, List<Product>> productsByCategory) {
        if (productsByCategory.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        for (String category : productsByCategory.keySet()) {
            System.out.println(category + ":");

            List<Product> products = productsByCategory.get(category);

            for (Product product : products) {
                System.out.println(product.getName() + ", " + product.getPrice() + " USD, " + product.getDescription());
            }
        }
    }

    private static void searchProducts(Scanner scanner, Map<String, List<Product>> productsByCategory) {
        System.out.println("Please enter a search query:");
        String query = scanner.nextLine();

        boolean foundProduct = false;

        for (String category : productsByCategory.keySet()) {
            List<Product> products = productsByCategory.get(category);

            for (Product product : products) {
                if (product.getName().toLowerCase().contains(query.toLowerCase()) ||
                        product.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    System.out.println(product.getName() + ", " + product.getPrice() + " USD, " + product.getDescription());
                    foundProduct = true;
                }
            }
        }

        if (!foundProduct) {
            System.out.println("No products found.");
        }
    }
}

