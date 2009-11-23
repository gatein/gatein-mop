/**
 * Copyright (C) 2009 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.gatein.mop.core.util;

import junit.framework.TestCase;

import java.util.Date;

import org.gatein.mop.api.Key;
import org.gatein.mop.api.ValueType;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class KeyTestCase extends TestCase
{

   public void testCreate()
   {
      Key<String> a = Key.create("a", ValueType.STRING);
      assertNotNull(a);
      assertEquals("a", a.getName());

      //
      Key<Integer> b = Key.create("a", ValueType.INTEGER);
      assertNotNull(b);
      assertEquals("a", b.getName());

      //
      Key<Boolean> c = Key.create("a", ValueType.BOOLEAN);
      assertNotNull(c);
      assertEquals("a", c.getName());

      //
      Key<Date> d = Key.create("a", ValueType.DATE);
      assertNotNull(d);
      assertEquals("a", d.getName());

      //
      Key<Double> e = Key.create("a", ValueType.DOUBLE);
      assertNotNull(e);
      assertEquals("a", e.getName());

      //
      try
      {
         Key.create("a", null);
         fail();
      }
      catch (NullPointerException ignore)
      {
      }
   }

}
