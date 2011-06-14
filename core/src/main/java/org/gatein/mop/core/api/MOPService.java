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

import org.chromattic.api.ChromatticBuilder;
import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticSession;
import org.gatein.mop.core.api.content.ContentManagerRegistry;
import org.gatein.mop.core.api.content.CustomizationContextProviderRegistry;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public abstract class MOPService
{

   /** . */
   ContentManagerRegistry contentManagerRegistry;

   /** . */
   CustomizationContextProviderRegistry customizationContextResolvers;

   public MOPService()
   {
      this(ChromatticBuilder.create());
   }

   /**
    * Build a mop service with a chromattic builder.
    *
    * @param builder the builder
    * @throws NullPointerException if the builder is null
    */
   public MOPService(ChromatticBuilder builder) throws NullPointerException
   {
      if (builder == null)
      {
         throw new NullPointerException();
      }

      //
   }

   public CustomizationContextProviderRegistry getCustomizationContextResolvers()
   {
      return customizationContextResolvers;
   }

   public ContentManagerRegistry getContentManagerRegistry()
   {
      return contentManagerRegistry;
   }

   protected void configure(CustomizationContextProviderRegistry registry)
   {
      //
   }

   protected void configure(ContentManagerRegistry registry)
   {
      //
   }

   protected abstract Chromattic getChromattic();

   public void start() throws Exception
   {
      CustomizationContextProviderRegistry customizationContextResolvers = new CustomizationContextProviderRegistry();

      //
      configure(customizationContextResolvers);

      //
      ContentManagerRegistry cmr = new ContentManagerRegistry();

      //
      configure(cmr);

      //
      this.contentManagerRegistry = cmr;
      this.customizationContextResolvers = customizationContextResolvers;
   }

   public ModelImpl getModel()
   {
      Chromattic chromattic = getChromattic();
      ChromatticSession chromeSession = chromattic.openSession();
      return new ModelImpl(
         this, chromeSession
      );
   }
}
