/**
 * Copyright 2011 OpenDDR LLC
 * This software is distributed under the terms of the GNU Lesser General Public License.
 *
 *
 * This file is part of OpenDDR Simple APIs.
 * OpenDDR Simple APIs is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * OpenDDR Simple APIs is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Simple APIs.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openddr.simpleapi.oddr.model;

import org.w3c.ddr.simple.PropertyRef;
import org.w3c.ddr.simple.PropertyValue;
import org.w3c.ddr.simple.exception.ValueException;

public class ODDRPropertyValue implements PropertyValue {

    private static final String TYPE_BOOLEAN = "xs:boolean";
    private static final String TYPE_DOUBLE = "xs:double";
    private static final String TYPE_ENUMERATION = "xs:enumeration";
    private static final String TYPE_FLOAT = "xs:float";
    private static final String TYPE_INT = "xs:integer";
    private static final String TYPE_NON_NEGATIVE_INTEGER = "xs:nonNegativeInteger";
    private static final String TYPE_LONG = "xs:long";
    private final String value;
    private final String type;
    private final PropertyRef propertyRef;

    public ODDRPropertyValue(String value, String type, PropertyRef propertyRef) {
        this.value = value;
        try {
            this.value.trim();

        } catch (NullPointerException ex) {
        }

        this.type = type;
        this.propertyRef = propertyRef;
    }

    /**
     * Get the value as a double. If the value doesn't exist it will
     * return 0.  If the type is badly defined it will throw a ValueException
     *
     * @return the value or 0
     * @throws ValueException
     */
    public double getDouble() throws ValueException {
        if (!exists()) {
            return 0;
        }

        if (type.equals(TYPE_DOUBLE) || type.equals(TYPE_FLOAT)) {
            try {
                return Double.parseDouble(value);

            } catch (NumberFormatException ex) {
                throw new ValueException(ValueException.INCOMPATIBLE_TYPES, ex);
            }
        }
        throw new ValueException(ValueException.INCOMPATIBLE_TYPES, "Not " + TYPE_DOUBLE + " value");
    }

    /**
     * Get the value as a long. If the value doesn't exist it will
     * return 0.  If the type is badly defined it will throw a ValueException
     *
     * @return the value or 0
     * @throws ValueException
     */
    public long getLong() throws ValueException {
        if (!exists()) {
            return 0;
        }
        if (type.equals(TYPE_LONG) || type.equals(TYPE_INT)) {
            try {
                return Long.parseLong(value);

            } catch (NumberFormatException ex) {
                throw new ValueException(ValueException.INCOMPATIBLE_TYPES, ex);
            }
        }
        throw new ValueException(ValueException.INCOMPATIBLE_TYPES, "Not " + TYPE_LONG + " value");
    }

    /**
     * Get the value as a boolean. If the value doesn't exist it will
     * return false.  If the type is badly defined it will throw a
     * ValueException
     *
     * @return the value (false if blank)
     * @throws ValueException
     */
    public boolean getBoolean() throws ValueException {
        if (!exists()) {
            //throw new ValueException(ValueException.NOT_KNOWN, type);
            return false;
        }
        if (type.equals(TYPE_BOOLEAN)) {
            try {
                return Boolean.parseBoolean(value);

            } catch (NumberFormatException ex) {
                throw new ValueException(ValueException.INCOMPATIBLE_TYPES, ex);
            }
        }
        throw new ValueException(ValueException.INCOMPATIBLE_TYPES, "Not " + TYPE_BOOLEAN + " value");
    }

    /**
     * Get the value as an integer. If the value doesn't exist it will
     * return 0.  If the type is badly defined it will throw a ValueException
     *
     * @return the value or 0
     * @throws ValueException
     */
    public int getInteger() throws ValueException {
        if (!exists()) {
            return 0;
        }

        if (type.equals(TYPE_INT)) {
            try {
                return Integer.parseInt(value);

            } catch (NumberFormatException ex) {
                throw new ValueException(ValueException.INCOMPATIBLE_TYPES, ex);
            }
        }

        if (type.equals(TYPE_NON_NEGATIVE_INTEGER)) {
            try {
                Integer integer = Integer.parseInt(value);

                if (integer >= 0) {
                    return Integer.parseInt(value);
                }

            } catch (NumberFormatException ex) {
                throw new ValueException(ValueException.INCOMPATIBLE_TYPES, ex);
            }
        }
        throw new ValueException(ValueException.INCOMPATIBLE_TYPES, "Not " + TYPE_INT + " value");
    }

    /**
     * Get the value as a string array. If the value doesn't exist it will
     * return null.  If the type is badly defined it will throw a ValueException
     *
     * @return the value or null
     * @throws ValueException
     */
    public String[] getEnumeration() throws ValueException {
        if (!exists()) {
            return null;
        }

        if (type.equals(TYPE_ENUMERATION)) {
            try {
                String[] splitted = value.split(",");
                for (int i = 0; i < splitted.length; i++) {
                    splitted[i] = splitted[i].trim();
                }

                return splitted;

            } catch (NumberFormatException ex) {
                throw new ValueException(ValueException.INCOMPATIBLE_TYPES, ex);
            }
        }
        throw new ValueException(ValueException.INCOMPATIBLE_TYPES, "Not " + TYPE_ENUMERATION + " value");
    }

    /**
     * Get the value as a float. If the value doesn't exist it will
     * return 0.  If the type is badly defined it will throw a ValueException
     *
     * @return the value or 0
     * @throws ValueException
     */
    public float getFloat() throws ValueException {
        if (!exists()) {
            //throw new ValueException(ValueException.NOT_KNOWN, type);
            return 0;
        }

        if (type.equals(TYPE_FLOAT)) {
            try {
                return Float.parseFloat(value);
            } catch (NumberFormatException ex) {
                throw new ValueException(ValueException.INCOMPATIBLE_TYPES, ex);
            }
        }

        throw new ValueException(ValueException.INCOMPATIBLE_TYPES, "Not " + TYPE_FLOAT + " value");
    }

    public PropertyRef getPropertyRef() {
        return propertyRef;
    }

    /**
     * Get the value as a string. If the value doesn't exist or
     * has a null value it will return a blank string.  If the type is
     * badly defined it will throw a ValueException
     *
     * @return the value or ""
     * @throws ValueException
     */
    public String getString() throws ValueException {
        if (!exists()) {
            //throw new ValueException(ValueException.NOT_KNOWN, type);
            return "";
        }
        return value;
    }

    public boolean exists() {
        return (value != null && value.length() > 0 && !"-".equals(value));
    }
}
