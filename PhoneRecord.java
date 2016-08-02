/**
 * @author Alexander Tsupko (alexander.tsupko@outlook.com)
 *         Copyright (c) All rights reserved.
 */
class PhoneRecord implements Comparable<PhoneRecord> {
    private String name;
    private String phone;

    PhoneRecord(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public int compareTo(PhoneRecord that) {
        if (this.name.compareToIgnoreCase(that.name) > 0) return 1;
        if (this.name.compareToIgnoreCase(that.name) < 0) return -1;
        if (this.phone.compareTo(that.phone) > 0) return 1;
        if (this.phone.compareTo(that.phone) < 0) return -1;
        return 0;
    }

    @Override
    public String toString() {
        return name + " " + phone;
    }

    String getPhone() {
        return phone;
    }

    String getName() {
        return name;
    }
}
