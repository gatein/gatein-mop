/*
 * Copyright (C) 2010 eXo Platform SAS.
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

import org.gatein.mop.api.workspace.WorkspaceObject;
import org.gatein.mop.spi.AdapterFactory;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class Foo
{

   /** . */
   final WorkspaceObject adaptee;

   /** . */
   final Class<Foo> adapterType;

   public Foo(WorkspaceObject adaptee, Class<Foo> adapterType)
   {
      this.adaptee = adaptee;
      this.adapterType = adapterType;
   }

   public static class Factory extends AdapterFactory<WorkspaceObject, Foo>
   {
      @Override
      public Foo getAdapter(WorkspaceObject adaptee, Class<Foo> adapterType)
      {
         return new Foo(adaptee, adapterType);
      }
   }
}
