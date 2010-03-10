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

import org.chromattic.api.NameConflictResolution;
import org.chromattic.api.annotations.Create;
import org.chromattic.api.annotations.FormattedBy;
import org.chromattic.api.annotations.NamingPolicy;
import org.chromattic.api.annotations.OneToMany;
import org.chromattic.api.annotations.PrimaryType;
import org.gatein.mop.core.api.MOPFormatter;
import org.gatein.mop.core.util.AbstractAttributes;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@PrimaryType(name = "mop:attributes")
@FormattedBy(MOPFormatter.class)
@NamingPolicy(onDuplicate = NameConflictResolution.REPLACE)
public abstract class AttributesImpl extends AbstractAttributes 
{

   @OneToMany
   public abstract Map<String, Attribute> getChildren();

   @Create
   public abstract IntegerAttribute createInteger();

   @Create
   public abstract BooleanAttribute createBoolean();

   @Create
   public abstract DateAttribute createDate();

   @Create
   public abstract StringAttribute createString();

   @Override
   protected Object get(String name)
   {
      Map<String, Attribute> children = getChildren();
      Attribute child = children.get(name);
      return child != null ? child.getValue() : null;
   }

   @Override
   protected void set(String name, Object o)
   {
      Map<String, Attribute> children = getChildren();
      if (o != null)
      {
         if (o instanceof Integer)
         {
            IntegerAttribute i = createInteger();
            children.put(name, i);
            i.setValue((Integer)o);
         }
         else if (o instanceof Boolean)
         {
            BooleanAttribute b = createBoolean();
            children.put(name, b);
            b.setValue((Boolean)o);
         }
         else if (o instanceof Date)
         {
            DateAttribute d = createDate();
            children.put(name, d);
            d.setValue((Date)o);
         }
         else
         {
            StringAttribute s = createString();
            children.put(name, s);
            s.setValue((String)o);
         }
      }
      else
      {
         children.remove(name);
      }
   }

   public Set<String> getKeys()
   {
      Map<String, Attribute> children = getChildren();
      return children.keySet();
   }
}
