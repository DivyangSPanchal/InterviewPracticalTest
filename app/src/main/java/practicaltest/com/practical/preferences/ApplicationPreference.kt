package practicaltest.com.practical.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

/**
 * Class foe storing primitive preference values for the application.
 *
 *
 * The singleton scope: a new instance of the bean is created the first time it
 * is needed. It is then retained and the same instance is always injected.
 */
class ApplicationPreference {

    internal var preferences: SharedPreferences? = null

    companion object {

        // Name of the preference file under data/data/application_preference package
        internal var preference_name = "practicaltest"

        /**
         * Put boolean value to preference.
         *
         * @param key     the key
         * @param value   the value
         * @param context the context
         */
        @SuppressLint("NewApi")
        fun setBoolean(key: String, value: Boolean, context: Context) {
            val preferences = context.getSharedPreferences(preference_name,
                    Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        /**
         * Gets the boolean value from preference.
         *
         * @param key      the key
         * @param defValue the default value
         * @param context  the context
         * @return the boolean
         */
        fun getBoolean(key: String, defValue: Boolean, context: Context): Boolean {
            val preferences = context.getSharedPreferences(preference_name,
                    Context.MODE_PRIVATE)
            return preferences.getBoolean(key, defValue)
        }

        /**
         * Put string value to preference.
         *
         * @param key     the key
         * @param value   the value
         * @param context the Context
         */
        @SuppressLint("NewApi")
        fun setString(key: String, value: String, context: Context) {
            val preferences = context.getSharedPreferences(
                    preference_name, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString(key, value)
            editor.apply()
        }

        /**
         * Gets the string value from preference.
         *
         * @param key      the key
         * @param defValue the default value
         * @param context  the context
         * @return the string
         */
        fun getString(key: String, defValue: String, context: Context): String {
            val preferences = context.getSharedPreferences(
                    preference_name, Context.MODE_PRIVATE)
            return preferences.getString(key, defValue)

        }


        /**
         * Put string value to preference.
         *
         * @param key     the key
         * @param value   the value
         * @param context the context
         */
        @SuppressLint("NewApi")
        fun setLong(key: String, value: Long, context: Context) {
            val preferences = context.getSharedPreferences(
                    preference_name, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putLong(key, value)
            editor.apply()
        }

        /**
         * Gets the string value from preference.
         *
         * @param key      the key
         * @param defValue the default value
         * @param context  the context
         * @return the string
         */
        fun getLong(key: String, defValue: Long, context: Context): Long {
            val preferences = context.getSharedPreferences(
                    preference_name, Context.MODE_PRIVATE)
            return preferences.getLong(key, defValue)

        }

        /**
         * Put integer value to preference.
         *
         * @param key     the key
         * @param value   the value
         * @param context the context
         */
        @SuppressLint("NewApi")
        fun setInteger(key: String, value: Int, context: Context) {

            val preferences = context.getSharedPreferences(preference_name,
                    Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putInt(key, value)
            editor.apply()
        }

        /**
         * Gets the integer value from preference.
         *
         * @param key      the key
         * @param defValue the default value
         * @param context  the context
         * @return the integer
         */
        fun getInteger(key: String, defValue: Int, context: Context): Int {

            val preferences = context.getSharedPreferences(preference_name,
                    Context.MODE_PRIVATE)
            return preferences.getInt(key, defValue)
        }

        fun clearDriverUserPreference(context: Context) {
            val preferences = context.getSharedPreferences(preference_name,
                    Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
        }
    }
}
