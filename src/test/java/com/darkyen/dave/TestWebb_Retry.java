package com.darkyen.dave;

public class TestWebb_Retry extends AbstractTestWebb {

    public void testRetryCount() throws Exception {
        long start = System.currentTimeMillis();
        String successAnswer = webb
                .get("/error/503/" + System.currentTimeMillis() + "/2")
                .retry(2, true)
                .ensureSuccess()
                .executeString()
                .getBody();

        assertEquals("Now it works", successAnswer);
        long duration = System.currentTimeMillis() - start;
        assertTrue("Should last longer than 3 seconds", duration > 3000);
    }

    public void testRetryCountArguments() throws Exception {
        webb.get("/simple.txt")
                .retry(3, false)
                .ensureSuccess()
                .executeString()
                .getBody();
        try {
            webb.get("/simple.txt")
                    .retry(4, false)
                    .ensureSuccess()
                    .executeString()
                    .getBody();
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // good!
        }
    }

}
