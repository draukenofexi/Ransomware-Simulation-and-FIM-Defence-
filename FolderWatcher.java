import java.nio.file.*;                     // File Handling: Provides modern tools to manage files and paths
import static java.nio.file.StandardWatchEventKinds.*; // Imports the 3 event triggers (Create, Modify, Delete)

public class FolderWatcher {
    public static void main(String[] args) {
        try {
            String targetPath = "C:\\Users\\Henil Prajapati\\OneDrive\\Documents\\Malware_Lab\\Target_Data";
            
            // 1. File Handling: Converting the string path into a Java 'Path' object
            Path path = Paths.get(targetPath);

            // 2. WatchDog Setup: Activating the OS-level folder monitoring sensor
            WatchService watchService = FileSystems.getDefault().newWatchService();

            // 3. Registering the sensor to our folder to look out for 3 specific actions
            path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);

            System.out.println("🛡️ WatchDog is active! Guarding folder: " + targetPath);

            // 4. INFINITE LOOP: while(true) keeps the program running 24/7. 
            // Without this, the WatchDog would check the folder once and go to sleep.
            while (true) {
                
                // 5. PAUSE & WAIT: The code stops at .take() and waits patiently for an event to occur.
                WatchKey key = watchService.take(); 

                // 6. GENERICS & WILDCARD (<?>): 
                // key.pollEvents() gives a list of all events that just happened.
                // <?> is a Wildcard. It tells Java: "I don't know the exact data type of this event, just catch whatever it is."
                for (WatchEvent<?> event : key.pollEvents()) {
                    
                    // Find out the 'Kind' of event (Was it a Create, Modify, or Delete action?)
                    WatchEvent.Kind<?> kind = event.kind(); 

                    // 7. EVENT CONTEXT: event.context() tells us 'WHO' triggered the event.
                    // We cast it to (Path) to extract the exact file name (e.g., dummy1.txt).
                    Path fileName = (Path) event.context();

                    // --- Checking the kind of event ---
                    if (kind == ENTRY_CREATE) {
                        System.out.println("🚨 ALERT: New file created -> " + fileName);
                        
                        // SMART DETECTION: If the newly created file ends with ".locked"
                        if (fileName.toString().endsWith(".locked")) {
                            System.out.println("⚠️ DANGER: Ransomware signature detected! Unauthorized .locked file created!");
                        }
                        
                    } else if (kind == ENTRY_MODIFY) {
                        System.out.println("⚠️ WARNING: File modified -> " + fileName);
                        
                    } else if (kind == ENTRY_DELETE) {
                        System.out.println("❌ NOTICE: File deleted -> " + fileName);
                    }
                }

                // 8. BOOLEAN VALIDATION & RESET: 
                // key.reset() prepares the WatchDog for the next event (like resetting a mousetrap).
                // It returns 'true' if the folder still exists. 
                // If someone deletes the "Target_Data" folder itself, it returns 'false'.
                boolean valid = key.reset();
                
                // 9. BREAKING THE LOOP: 
                // If valid is false (!valid), it breaks the while(true) loop to prevent the program from crashing.
                if (!valid) {
                    System.out.println("🛑 CRITICAL: Monitored folder was deleted. Shutting down WatchDog.");
                    break; 
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}