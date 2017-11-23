package com.test.testapplication.logger;

import android.util.Log;

/**
 * Created by moseskesavan on 11/21/17.
 *
 * This class is used to log info,error,verbose within the application.
 * If required the class may be disable/enabled using the IS_DEBUG_ENABLED flag.
 */

public class AppLog {

        public static boolean IS_DEBUG_ENABLED = true;


        /**
         * Returns the time required to perform the operation.
         *
         * @param startTime time at which the operation started
         * @param endTime   time at which operation completed
         * @return String time required to perform operation
         */
        private static String timeRequired(long startTime, long endTime) {
            return "Time Required::" + ((float) (endTime - startTime)) + "mS";
        }

        /**
         * Method give Stack Trace
         *
         * @param e Exception instance
         * @return returns the exception as a string message.
         */
        public static String getStackTrace(Exception e) {
            return Log.getStackTraceString(e);
        }

        public static int e(java.lang.String tag, java.lang.String msg)
        {
            if (isDebugBuild() && !isEmpty(msg)) {
                return Log.e(tag, msg);
            }
            return -1;
        }

        public static int d(java.lang.String tag, java.lang.String msg)
        {
            if (isDebugBuild() && !isEmpty(msg)) {
                return Log.d(tag, msg);
            }
            return -1;
        }

        public static int i(java.lang.String tag, java.lang.String msg)
        {
            if (isDebugBuild() && !isEmpty(msg)) {
                return Log.i(tag, msg);
            }
            return -1;
        }

        public static int v(java.lang.String tag, java.lang.String msg)
        {
            if (isDebugBuild() && !isEmpty(msg)) {
                return Log.v(tag, msg);
            }
            return -1;
        }

        public static int v(java.lang.String tag, java.lang.String msg,Exception ex)
        {
            if (isDebugBuild() && !isEmpty(msg)) {
                return Log.v(tag, msg,ex);
            }
            return -1;
        }

        public static int w(java.lang.String tag, java.lang.String msg)
        {
            if (isDebugBuild() && !isEmpty(msg)) {
                return Log.w(tag, msg);
            }
            return -1;
        }

        private static boolean isDebugBuild() {
            return IS_DEBUG_ENABLED;
        }

        private static boolean isEmpty(String str) {
            return !(str != null && str.length() != 0);
        }


}
