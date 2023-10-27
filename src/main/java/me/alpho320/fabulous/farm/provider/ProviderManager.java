package me.alpho320.fabulous.farm.provider;

import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.FarmPlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.IntStream;

public class ProviderManager {

    private static final @NotNull Map<String, Provider> MAP = new HashMap<>();
    private static Provider PROVIDER;

    public static void setup(@NotNull FarmPlugin plugin, boolean loadAllData, @Nullable Provider.Callback callback) {
        String key = plugin.getConfig().getString("Data.provider", "null").toLowerCase();
        long now = System.currentTimeMillis();

        Provider provider = find(key);
        Debug.debug(0, " | Selected Provider: " + provider + " - " + (provider != null ? provider.name() : "null!"));

        if (provider != null) {

            setProvider(provider);
            Debug.debug(0, " | SettedProvider: " + get() + " - ");
            get().init(callback);

            if (loadAllData) {
                get().loadAllData(true, state -> {
                    if (state) {
                        Debug.debug(0, " | All data successfully loaded. (" + FarmAPI.took(now) + ")");

                        TaskManager.startTasks(plugin);
                        FarmAPI.checkCallback(callback, true);

                    } else {
                        IntStream.rangeClosed(0, 20).forEach(i -> Debug.debug(1, " "));
                        Debug.debug(1, "Failed to load all data!");
                        FarmAPI.checkCallback(callback, false);
                    }
                });
            } else {
                TaskManager.startTasks(plugin);
                FarmAPI.checkCallback(callback, true);
                FarmAPI.runASYNC(() -> Realms.createAllRealms(plugin.getConfig(), plugin));
            }

        } else {
            FarmAPI.checkCallback(callback, false);
            throw new IllegalStateException(key + " is not valid provider. (Providers: " + map().keySet() + ")");
        }

    }

    public static @Nullable Provider find(@NotNull String name) {
        return map().getOrDefault(name, null);
    }

    public static @Nullable PlayerData findPlayerData(@NotNull Player player) {
        return get().findPlayerData(player);
    }

    public static @Nullable PlayerData findPlayerData(@NotNull UUID uuid) {
        return get().findPlayerData(uuid);
    }

    public static void register(@NotNull String id, @NotNull Provider provider) {
        MAP.put(Objects.requireNonNull(id, "id cannot be null!"), Objects.requireNonNull(provider, "Provider cannot be null!"));
    }

    public static void register(@NotNull Provider...providers) {
        Arrays.stream(providers).forEach(provider -> MAP.put(provider.name(), provider));
    }

    public static void register(@NotNull Provider provider) {
        MAP.put(provider.name(), Objects.requireNonNull(provider, "Provider cannot be null!"));
    }



    /*public static String serialize(@NotNull Object object) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream myObjectOutStream = new ObjectOutputStream(outputStream);

            myObjectOutStream.writeObject(object);

            myObjectOutStream.close();
            outputStream.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object deserialize(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(inputStream.readAllBytes()));

            Object deSerializedObject = objectIn.readObject();

            Debug.debug(2, "Deserialized: " + deSerializedObject);
            return deSerializedObject;
        } catch (InvalidClassException e) {
            e.printStackTrace();
            return null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
*/

/*    public static String serialize(Object object) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeObject(object);

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save object.", e);
        }
    }

    public static Object deserialize(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Object object = dataInput.readObject();

            dataInput.close();
            return object;
        } catch (ClassNotFoundException | IOException e) {
            try {
                throw new IOException("Unable to decode class type.", e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return null;
    }*/

    public static @NotNull Provider get() {
        if (PROVIDER == null) throw new IllegalStateException("Provider is not initialized!");
        return PROVIDER;
    }

    public static void setProvider(@NotNull Provider provider) {
        PROVIDER = provider;
    }

    public static @NotNull Map<String, Provider> map() {
        return MAP;
    }

}