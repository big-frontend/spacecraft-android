package com.electrolytej;

public interface GcTrigger {
    GcTrigger DEFAULT = new GcTrigger() {
        public void runGc() {
            Runtime.getRuntime().gc();
            this.enqueueReferences();
            System.runFinalization();
        }

        private void enqueueReferences() {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException var2) {
                throw new AssertionError();
            }
        }
    };

    void runGc();
}