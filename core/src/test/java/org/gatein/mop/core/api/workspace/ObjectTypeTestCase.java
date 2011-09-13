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
package org.gatein.mop.core.api.workspace;

import junit.framework.TestCase;
import org.gatein.mop.api.workspace.ObjectType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ObjectTypeTestCase extends TestCase
{

   public void testAssignability()
   {
      assertFalse(ObjectType.WORKSPACE.isAssignableFrom(ObjectType.SITE));
      assertFalse(ObjectType.SITE.isAssignableFrom(ObjectType.WORKSPACE));

      //
      assertTrue(ObjectType.SITE.isAssignableFrom(ObjectType.SITE));

      //
      assertTrue(ObjectType.SITE.isAssignableFrom(ObjectType.PORTAL_SITE));
      assertFalse(ObjectType.PORTAL_SITE.isAssignableFrom(ObjectType.SITE));

      //
      assertTrue(ObjectType.SITE.isAssignableFrom(ObjectType.GROUP_SITE));
      assertFalse(ObjectType.GROUP_SITE.isAssignableFrom(ObjectType.SITE));
   }

   public void testSerialization() throws Exception
   {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream out = new ObjectOutputStream(baos);
      out.writeObject(ObjectType.PAGE);
      out.close();
      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      ObjectInputStream in = new ObjectInputStream(bais);
      Object o = in.readObject();
      assertSame(ObjectType.PAGE, o);
   }
}
