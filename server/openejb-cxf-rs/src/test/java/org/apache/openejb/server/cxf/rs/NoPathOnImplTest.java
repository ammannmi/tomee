/*
 *     Licensed to the Apache Software Foundation (ASF) under one or more
 *     contributor license agreements.  See the NOTICE file distributed with
 *     this work for additional information regarding copyright ownership.
 *     The ASF licenses this file to You under the Apache License, Version 2.0
 *     (the "License"); you may not use this file except in compliance with
 *     the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package org.apache.openejb.server.cxf.rs;

import org.apache.openejb.jee.WebApp;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.loader.IO;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.EnableServices;
import org.apache.openejb.testing.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

@EnableServices("jax-rs")
@RunWith(ApplicationComposer.class)
public class NoPathOnImplTest {
    @Module
    @Classes({ API.class, Impl.class })
    public static WebApp service() throws Exception {
        return new WebApp().contextRoot("app");
    }

    @Test
    public void check() throws Exception {
        final HttpURLConnection conn = HttpURLConnection.class.cast(new URL("http://127.0.0.1:4204/app/api").openConnection());
        assertEquals("ok", IO.slurp(conn.getInputStream()));
        conn.getInputStream().close();
    }

    @Path("api")
    public static interface API {
        @GET
        String providers();
    }

    public static class Impl implements API {
        @Override
        public String providers() {
            return "ok";
        }
    }
}
