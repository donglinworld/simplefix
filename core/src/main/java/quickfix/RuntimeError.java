/*******************************************************************************
 * Copyright (c) quickfixengine.org  All rights reserved. 
 * 
 * This file is part of the QuickFIX FIX Engine 
 * 
 * This file may be distributed under the terms of the quickfixengine.org 
 * license as defined by quickfixengine.org and appearing in the file 
 * LICENSE included in the packaging of this file. 
 * 
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING 
 * THE WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A 
 * PARTICULAR PURPOSE. 
 * 
 * See http://www.quickfixengine.org/LICENSE for licensing information. 
 * 
 * Contact ask@quickfixengine.org if any conditions of this licensing 
 * are not clear to you.
 ******************************************************************************/

package quickfix;

/**
 * 
 * Application encountered serious error during runtime.
 * (The "error" naming is from the C++ JNI API.)
 */
public class RuntimeError extends RuntimeException {

    public RuntimeError() {
        super();
    }

    public RuntimeError(String message) {
        super(message);
    }

    public RuntimeError(Throwable e) {
        super(e);
    }

    public RuntimeError(String message, Throwable cause) {
        super(message, cause);
    }
}
