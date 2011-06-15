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

import org.gatein.mop.spi.AdapterLifeCycle;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class Bar
{

   /** . */
   final WorkspaceObjectImpl adaptee;

   /** . */
   final Class<Bar> adapterType;

   public Bar(WorkspaceObjectImpl adaptee, Class<Bar> adapterType)
   {
      this.adaptee = adaptee;
      this.adapterType = adapterType;
   }

   public static class Factory extends AdapterLifeCycle<WorkspaceObjectImpl, Bar>
   {
      public Bar create(WorkspaceObjectImpl adaptee, Class<Bar> adapterType)
      {
         return new Bar(adaptee, adapterType);
      }

      @Override
      public void destroy(Bar adapter, WorkspaceObjectImpl adaptee, Class<Bar> adapterType)
      {
      }
   }
}
