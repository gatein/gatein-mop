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
import org.gatein.mop.api.ValueType;

import java.util.Date;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ValueTypeTestCase extends TestCase
{


   public void testGet()
   {
      assertEquals(ValueType.INTEGER, ValueType.get(5));
      assertEquals(ValueType.BOOLEAN, ValueType.get(true));
      assertEquals(ValueType.STRING, ValueType.get(""));
      assertEquals(ValueType.DATE, ValueType.get(new Date()));
      assertEquals(ValueType.DOUBLE, ValueType.get(0.5D));
      try
      {
         ValueType.get(null);
         fail();
      }
      catch (NullPointerException ignore)
      {
      }
      try
      {
         ValueType.get(new Object());
         fail();
      }
      catch (IllegalArgumentException ignore)
      {
      }
   }

   public void testDecode()
   {
      assertEquals(ValueType.INTEGER, ValueType.decode(5));
      assertEquals(ValueType.BOOLEAN, ValueType.decode(true));
      assertEquals(ValueType.STRING, ValueType.decode(""));
      assertEquals(ValueType.DATE, ValueType.decode(new Date()));
      assertEquals(ValueType.DOUBLE, ValueType.decode(0.5D));
      assertEquals(null, ValueType.decode(null));
      assertEquals(null, ValueType.decode(new Object()));
   }

   public void testAccept()
   {
      assertTrue(ValueType.INTEGER.isInstance(5));
      assertFalse(ValueType.INTEGER.isInstance(""));
      assertFalse(ValueType.INTEGER.isInstance(new Date()));
      assertFalse(ValueType.INTEGER.isInstance(true));
      assertFalse(ValueType.INTEGER.isInstance(0.5D));
      assertFalse(ValueType.INTEGER.isInstance(null));
      assertFalse(ValueType.INTEGER.isInstance(new Object()));

      //
      assertFalse(ValueType.BOOLEAN.isInstance(5));
      assertFalse(ValueType.BOOLEAN.isInstance(""));
      assertFalse(ValueType.BOOLEAN.isInstance(new Date()));
      assertTrue(ValueType.BOOLEAN.isInstance(true));
      assertFalse(ValueType.BOOLEAN.isInstance(0.5D));
      assertFalse(ValueType.BOOLEAN.isInstance(null));
      assertFalse(ValueType.BOOLEAN.isInstance(new Object()));

      //
      assertFalse(ValueType.DATE.isInstance(5));
      assertFalse(ValueType.DATE.isInstance(""));
      assertTrue(ValueType.DATE.isInstance(new Date()));
      assertFalse(ValueType.DATE.isInstance(true));
      assertFalse(ValueType.DATE.isInstance(0.5D));
      assertFalse(ValueType.DATE.isInstance(null));
      assertFalse(ValueType.DATE.isInstance(new Object()));

      //
      assertFalse(ValueType.STRING.isInstance(5));
      assertTrue(ValueType.STRING.isInstance(""));
      assertFalse(ValueType.STRING.isInstance(new Date()));
      assertFalse(ValueType.STRING.isInstance(true));
      assertFalse(ValueType.STRING.isInstance(0.5D));
      assertFalse(ValueType.STRING.isInstance(null));
      assertFalse(ValueType.STRING.isInstance(new Object()));

      //
      assertFalse(ValueType.DOUBLE.isInstance(5));
      assertFalse(ValueType.DOUBLE.isInstance(""));
      assertFalse(ValueType.DOUBLE.isInstance(new Date()));
      assertFalse(ValueType.DOUBLE.isInstance(true));
      assertTrue(ValueType.DOUBLE.isInstance(0.5D));
      assertFalse(ValueType.DOUBLE.isInstance(null));
      assertFalse(ValueType.DOUBLE.isInstance(new Object()));
   }

   public void testCast()
   {
      // todo
   }
}
