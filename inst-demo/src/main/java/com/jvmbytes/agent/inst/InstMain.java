package com.jvmbytes.agent.inst;

/**
 * @author wongoo
 */
public class InstMain {

    public static void main(String[] args) throws Exception {
        System.out.println("inst:" + InstLoader.loadInst());
        System.out.println("feature:" + InstLoader.loadFeature());

        while (true) {
            System.out.print(".");
            Thread.sleep(10 * 1000);
        }
    }
}
