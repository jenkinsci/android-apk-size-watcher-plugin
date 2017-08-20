package com.dg.watcher.watching.saving;


public class SizeEntry {
    private String buildName;
    private Long   sizeInByte;


    public SizeEntry(String buildName, Long sizeInByte) {
        this.buildName = buildName;
        this.sizeInByte = sizeInByte;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }

        if(o == null || getClass() != o.getClass()) {
            return false;
        }

        SizeEntry that = (SizeEntry) o;

        return    buildName.equals(that.buildName)
               && sizeInByte.equals(that.sizeInByte);
    }

    @Override
    public int hashCode() {
        return 31 * buildName.hashCode() + sizeInByte.hashCode();
    }

    @Override
    public String toString() {
        return "Name: " + buildName + " Size: " + sizeInByte + " Byte";
    }

    public String getBuildName() {
        return buildName;
    }

    public Long getSizeInByte() {
        return sizeInByte;
    }
}