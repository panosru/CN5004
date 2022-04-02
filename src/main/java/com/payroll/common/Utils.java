package com.payroll.common;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Locale;

/**
 * Utility class for common methods and constants.
 */
public final class Utils
{
    public static final int APP_WIDTH = 960;
    public static final int APP_HEIGHT = 600;
    public static final String TITLE = "CN5004 - Payroll";
    public static final String OS = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
    public static final String USER_HOME_DIR = System.getProperty("user.home");
    public static final String USER_DOWNLOAD_DIR = USER_HOME_DIR + File.separator + "Downloads";

    /**
     * Utility classes, which are collections of static members, are not meant to be instantiated.
     */
    private Utils()
    {

    }

    /**
     * Checks if the OS is Windows.
     * @return true if the OS is Windows, false otherwise.
     */
    public static boolean isWindows()
    {
        return Utils.OS.contains("win");
    }

    /**
     * Checks if the OS is Mac.
     * @return true if the OS is Mac, false otherwise.
     */
    public static boolean isMac()
    {
        return Utils.OS.contains("mac") || Utils.OS.contains("darwin");
    }

    /**
     * Capitalise each word in a string.
     * @param s the string to be capitalised.
     * @return the capitalised string.
     */
    public static @NotNull String capitalise(@NotNull String s)
    {
        if (s.length() == 0)
            return s;
        String[] words = s.split(" ");
        StringBuilder sb = new StringBuilder();

        for (String word : words)
        {
            word = normaliseLower(word);

            sb.append(Character.toUpperCase(word.charAt(0)))
              .append(word.substring(1)).append(" ");
        }

        return sb.toString().trim();
    }

    /**
     * Normalise a string to lower case.
     * @param s the string to be normalised.
     * @return the normalised string.
     */
    public static @NotNull String normaliseLower(@NotNull String s)
    {
        return s.trim().toLowerCase(Locale.getDefault());
    }

    /**
     * Normalise a string to upper case.
     * @param s the string to be normalised.
     * @return the normalised string.
     */
    public static @NotNull String normaliseUpper(String s)
    {
        return normaliseLower(s).toUpperCase();
    }

    /**
     * This method is used to generate a string representing a hash of a string (e.g. password)
     * suitable for storing in a database. It will be an OpenBSD-style crypt(3) formatted
     * hash string of length=60
     * A workload of 12 is a very reasonable safe default as of 2013.
     * This automatically handles secure 128-bit salt generation and storage within the hash.
     * @param s The plaintext string to hash.
     * @return String - a string of length 60 that is the bcrypt hashed string in crypt(3) format.
     */
    public static String hashString(String s) {
        // Define the BCrypt workload to use when generating password hashes. 10-31 is a valid value.
        return BCrypt.withDefaults().hashToString(12, s.toCharArray());
    }

    /**
     * This method can be used to verify a computed hash from a plaintext (e.g. during a login
     * request) with that of a stored hash from a database. The hash from the database
     * must be passed as the second variable.
     * @param s The plaintext string.
     * @param stored_hash The stored string hash, retrieved from the database.
     * @return boolean - true if the string matches the string of the stored hash, false otherwise
     */
    public static boolean verifyHash(String s, String stored_hash) {
        return (BCrypt.verifyer().verify(s.toCharArray(), stored_hash)).verified;
    }
}
