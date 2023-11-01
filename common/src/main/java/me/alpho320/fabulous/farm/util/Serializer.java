package me.alpho320.fabulous.farm.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Serializer<SERIALIZED_OBJECT, DESERIALIZED_OBJECT> {

    public abstract @NotNull SERIALIZED_OBJECT serialize(@NotNull Object object);
    public abstract @Nullable DESERIALIZED_OBJECT deserialize(@NotNull Object object);

}