package com.github.shepherdviolet.webdemo.incubating.session;

public class Test {

    public static void main(String[] args) throws Exception {

        SessionManager sessionManager = new SessionManager();
        sessionManager.setSessionStore(new TestSessionStore());

        String token = sessionManager.newSession(sessionManager.toHashId("aaa"));
        System.out.println(token);
        Thread.sleep(500);
//        Thread.sleep(2000);
//        sessionManager.cleanSession(sessionManager.toHashId("aaa"));
        SessionInfo sessionInfo = sessionManager.verifyToken(token);
        System.out.println(sessionInfo);

    }

}
