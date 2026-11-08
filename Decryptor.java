import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Decryptor {

    // 1. The exact same Secret Key used during encryption (Must match 100%)
    private static final String SECRET_KEY = "BankaiGetsuga123";

    public static void main(String[] args) {
        // Path of the target folder where locked files are stored
        String targetPath = "C:\\Users\\Henil Prajapati\\OneDrive\\Documents\\Malware_Lab\\Target_Data";
        
        // Create a File object that points to the target directory path
        File targetFolder = new File(targetPath);

        System.out.println("🔓 Decryption Process Started...");

        // Start the spider method to scan and process the target folder for locked files
        decryptSpider(targetFolder);

        System.out.println("✨ All files have been successfully unlocked and restored!");
    }

    // 2. Spider Logic (Directory Traversal - scanning folders and subfolders for .locked files)
    static void decryptSpider(File folder) {
        // Get an array of all files and folders present inside the current directory
        File[] files = folder.listFiles();

        // If the folder is empty or cannot be accessed, stop and go back
        if (files == null) return;

        // Loop through every file and subfolder one by one
        for (File file : files) {
            if (file.isDirectory()) {
                // If there is another folder inside this folder, go inside it too (Recursion)
                decryptSpider(file);
            } else {
                // If it is a file and it is currently locked (ends with .locked), decrypt it
                if (file.getName().endsWith(".locked")) {
                    decryptAndUnlock(file);
                }
            }
        }
    }

    // 3. Byte-Level File I/O & AES Decryption Logic
    static void decryptAndUnlock(File file) {
        try {
            // A. Prepare the AES decryption lock (Using the secret key)
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            
            // Create a Cipher object configured to use the AES algorithm
            Cipher cipher = Cipher.getInstance("AES");
            
            // Initialize the cipher in DECRYPT_MODE (unlock mode) using our secret key
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            // B. Read the encrypted binary data (garbage bytes) from the locked file
            FileInputStream inputStream = new FileInputStream(file);
            byte[] inputBytes = new byte[(int) file.length()];
            
            // Read all bytes from the locked file and store them into inputBytes
            inputStream.read(inputBytes);
            
            // Close the input stream to release the file from memory
            inputStream.close();

            // C. Decrypt (unscramble) the data using AES
            // Process the encrypted bytes and convert them back to original readable bytes
            byte[] outputBytes = cipher.doFinal(inputBytes);

            // D. Create the original file back and remove the '.locked' extension
            // Remove the last 7 characters (".locked") from the file's absolute path
            String originalPath = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 7);
            File restoredFile = new File(originalPath);

            // Open an output stream to write the original data into the restored file
            FileOutputStream outputStream = new FileOutputStream(restoredFile);
            
            // Write the decrypted original bytes into the restored file
            outputStream.write(outputBytes);
            
            // Close the output stream to save changes properly
            outputStream.close();

            // Delete the locked file since it has now been successfully restored
            file.delete();

        } catch (Exception e) {
            // If any error occurs during decryption, print the stack trace for debugging
            e.printStackTrace();
        }
    }
}
