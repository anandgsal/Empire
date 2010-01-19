/*
 * Copyright (c) 2009-2010 Clark & Parsia, LLC. <http://www.clarkparsia.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clarkparsia.empire;

import com.clarkparsia.empire.util.PropertiesAnnotationProvider;
import com.clarkparsia.empire.util.EmpireAnnotationProvider;

/**
 * <p>Catch-all class for global Empire options and configuration</p>
 *
 * @author Michael Grove
 * @since 0.1
 */
public class EmpireOptions {

	/**
	 * Whether or not to force strong typing of literals during I/O from the database.  When this is true, literals
	 * written to the database will always contain a datatype, and on input are expected to have a datatype or else
	 * the conversion will fail.  When false, datatype information will be ignored both during reads and writes.
	 * The recommended value is true because that will give the most accurate conversions, and allow the most
	 * appropriate design of your Java beans, but if you are using 3rd party data which does not use datatypes
	 * disabling this mode can be useful.  The default value is true.
	 */
    public static boolean STRONG_TYPING = true;

	/**
	 * The {@link EmpireAnnotationProvider} to use to get information about Annotations in the system.
	 */
	public static EmpireAnnotationProvider ANNOTATION_PROVIDER = new PropertiesAnnotationProvider();

	/**
	 * Controls whether or not Proxy objects will be generated by the EntityManager.  Proxy objects make for less
	 * expensive retrievals from the database; objects are only pulled down from the database when they are requested.
	 *
	 * For example, if you are grabbing an instance of an object from the db, and it has several fields which all
	 * need to be grabbed from the db, when this flag is false, every object will be retrieved, and if any of those objects
	 * have fields which are objects to be grabbed from the db, those will be retrieved as well.  So when this is set
	 * to true, when these objects are encountered that need to be pulled from the db, a proxy object is generated and
	 * the value of the object is populated when the object is requested (lazy loading).
	 *
	 * The proxy objects only do proxying for methods, so if you use direct field access, the proxy will not be involved
	 * and you could end up with null or incorrect values, and unexpected errors will likely occur.  Also, since
	 * the proxy objects are subclasses of the actual objects, you'll want to make sure that your equals method accepts
	 * subclasses of the type rather than exact type matching.
	 */
	public static boolean ENABLE_PROXY_OBJECTS = false;

	/**
	 * Controls whether or not the super class and interfaces of a bean class are inspected for annotations such as
	 * {@link com.clarkparsia.empire.annotation.RdfProperty} or {@link com.clarkparsia.empire.annotation.RdfsClass}.
	 */
	public static boolean INSPECT_BEAN_HIERARCHY = true;
}
