package com.demo.jetpack.network

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

/**
 * 数据处理
 */
class GsonAdapter : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        return when (type.rawType as Class<T>) {
            String::class.java -> StringNullAdapter() as TypeAdapter<T>
            Double::class.java -> DoubleAdapter() as TypeAdapter<T>
            Int::class.java -> IntAdapter() as TypeAdapter<T>
            else -> null
        }
    }
}

private class IntAdapter : TypeAdapter<Int>() {
    override fun write(out: JsonWriter?, value: Int?) {
        out?.value(value)
    }

    override fun read(reader: JsonReader?): Int {
        if (reader == null) return 0
        val peek = reader.peek()
        if (peek == JsonToken.NULL) {
            reader.nextNull()
            return 0
        }
        if (peek == JsonToken.STRING) {
            val text = reader.nextString()
            if (text.isNotBlank() && TextUtils.isDigitsOnly(text)) {
                return text.toInt()
            }
            gsonError(reader.path, "Int", text)
            return 0
        }
        return reader.nextInt()
    }

}

private class DoubleAdapter : TypeAdapter<Double>() {
    override fun write(out: JsonWriter?, value: Double?) {
        out?.value(value)
    }

    override fun read(reader: JsonReader?): Double {
        if (reader == null) return 0.0
        val peek = reader.peek()
        if (peek == JsonToken.NULL) {
            reader.nextNull()
            gsonError(reader.path, "Double", "null")
            return 0.0
        } else if (peek == JsonToken.STRING) {
            val text = reader.nextString()
            if (text.isNotBlank()) {
                return text.toDouble()
            }
            gsonError(reader.path, "Double", text)
            return 0.0
        }
        return reader.nextDouble()
    }

}

private class StringNullAdapter : TypeAdapter<String>() {
    @Throws(IOException::class)
    override fun read(reader: JsonReader): String {
        val peek = reader.peek()
        if (peek == JsonToken.NULL) {
            reader.nextNull()
            return ""
        }
        return reader.nextString()
    }

    @Throws(IOException::class)
    override fun write(writer: JsonWriter, value: String?) {
        if (value == null) {
            writer.value("")
            return
        }
        writer.value(value)
    }
}

fun gsonError(path: String, type: String, realText: String) {
    Log.e("数据异常", "解析 json 错误：字段 $path, 期望类型 $type, 实际值 [$realText]")
}