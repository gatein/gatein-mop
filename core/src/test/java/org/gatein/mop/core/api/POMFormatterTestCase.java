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
package org.gatein.mop.core.api;

import junit.framework.TestCase;
import org.chromattic.api.format.ObjectFormatter;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class POMFormatterTestCase extends TestCase
{

   /** . */
   private final ObjectFormatter formatter = new MOPFormatter();

   private void assertString(String expected, String s)
   {
      assertEquals(expected, formatter.encodeNodeName(null, s));
      assertEquals(s, formatter.decodeNodeName(null, expected));
   }

   private void assertCannotDecode(String s)
   {
      try
      {
         formatter.decodeNodeName(null, s);
         fail();
      }
      catch (IllegalStateException ignore)
      {
      }
   }

   public void testStrings()
   {
      assertString("mop:", "");
      assertString("mop:a", "a");
      assertString("mop:%00", "{");
      assertString("mop:%01", "}");
      assertString("mop:%02", ".");
      assertString("mop:%03", "/");
      assertString("mop:%04", ":");
      assertString("mop:%05", "[");
      assertString("mop:%06", "]");
      assertString("mop:%07", "|");
      assertString("mop:%08", "*");
      assertString("mop:%09", "%");
      assertString("mop:a%03b", "a/b");
   }

   public void testDecodeFailure()
   {
      assertCannotDecode("%0");
      assertCannotDecode("%0" + (char)('0' - 1));
      assertCannotDecode("%0" + (char)('9' + 1));
      assertCannotDecode("%1");
   }
}
