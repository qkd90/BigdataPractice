package com.zuipin.util;

import com.framework.struts.JsonBooleanValueProcessor;
import com.framework.struts.JsonDateValueProcessor;
import com.framework.struts.JsonIntegerValueProcessor;
import com.framework.struts.JsonLongValueProcessor;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

public class JsonFilter {

	private final static Log log = LogFactory.getLog(JsonFilter.class);

	public static JsonConfig getFilterConfig(final String... excludes) {
		JsonConfig config = new JsonConfig();
		// 处理日期在前台转换为object问题
		if (excludes != null) {
			config.setJsonPropertyFilter(new PropertyFilter() {

				@Override
				public boolean apply(Object source, String name, Object value) {
					for (String exclude : excludes) {
						if (exclude.equals(name)) {
							return true;
						}
					}
					return false;
				}
			});
		}
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		// 处理Boolean为null情况在前台显示false问题
		config.registerJsonValueProcessor(Boolean.class, new JsonBooleanValueProcessor());
		return config;
	}

    public static JsonConfig getExcludeConfig(final Class<?>[] excludeClass, final String... excludeProperties) {
        final JsonConfig config = new JsonConfig();
        config.setJsonPropertyFilter(new PropertyFilter() {
            @Override
            public boolean apply(Object source, String name, Object value) {
                Class<? extends Object> clazz = source.getClass();
                try {
                    Field field = clazz.getDeclaredField(name);
                    if (excludeClass != null) {
                        for (Class<?> class1 : excludeClass) {
                            if (field.getType() == class1) {
                                return true;
                            }
                        }
                    }
                    if (excludeProperties != null) {
                        for (String property : excludeProperties) {
                            if (field.getName().equals(property)) {
                                return true;
                            }
                        }
                    }
                    if (field.isAnnotationPresent(OneToOne.class) || field.isAnnotationPresent(OneToMany.class)
                            || field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(ManyToMany.class)) {
                        return true;
                    }
                    String fieldName = field.getName();
                    if (fieldName.length() > 1) {
                        String getMethodName = String.format("get%s%s", fieldName.substring(0, 1).toUpperCase(),
                                fieldName.substring(1, fieldName.length()));
                        try {
                            Method methodName = clazz.getMethod(getMethodName);
                            if (methodName.isAnnotationPresent(OneToOne.class) || methodName.isAnnotationPresent(OneToMany.class)
                                    || methodName.isAnnotationPresent(ManyToOne.class) || methodName.isAnnotationPresent(ManyToMany.class)) {

                                return true;
                            }
                        } catch (NoSuchMethodException e) {
                            if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                                getMethodName = String.format("is%s%s", fieldName.substring(0, 1).toUpperCase(),
                                        fieldName.substring(1, fieldName.length()));
                                try {
                                    Method methodName = clazz.getMethod(getMethodName);
                                    if (methodName.isAnnotationPresent(OneToOne.class) || methodName.isAnnotationPresent(OneToMany.class)
                                            || methodName.isAnnotationPresent(ManyToOne.class) || methodName.isAnnotationPresent(ManyToMany.class)) {

                                        return true;
                                    }
                                } catch (NoSuchMethodException ne) {
                                    log.error("找不到" + fieldName + "的is方法，发返回json");
                                    return true;
                                }
                            } else {
                                log.error("找不到" + fieldName + "的get方法，发返回json");
                                return true;
                            }
                        }
                    }
                } catch (NoSuchFieldException e) {
                    log.debug("在当前类<" + clazz.getName() + ">找不到字段" + name + ", 到父类查找");
                    Boolean flag = applyParent(clazz.getSuperclass(), name, value);
                    if (flag != null) {
                        return flag.booleanValue();
                    }
                } catch (Exception e) {
                    log.error("处理失败<" + clazz.getName() + "." + name + ">, 直接返回值");
                }
                for (Class<?> interfaze : clazz.getInterfaces()) {
                    if (interfaze == HibernateProxy.class) {
                        return true;
                    }
                }
                return false;
            }
            private Boolean applyParent(Class clazz, String name, Object value) {
                if (clazz == Object.class) {
                    return null;
                }
                try {
                    Field field = clazz.getDeclaredField(name);
                    if (excludeClass != null) {
                        for (Class<?> class1 : excludeClass) {
                            if (field.getType() == class1) {
                                return true;
                            }
                        }
                    }
                    if (excludeProperties != null) {
                        for (String property : excludeProperties) {
                            if (field.getName().equals(property)) {
                                return true;
                            }
                        }
                    }
                    if (field.isAnnotationPresent(OneToOne.class) || field.isAnnotationPresent(OneToMany.class)
                            || field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(ManyToMany.class)) {
                        return true;
                    }
                    String fieldName = field.getName();
                    if (fieldName.length() > 1) {
                        String getMethodName = String.format("get%s%s", fieldName.substring(0, 1).toUpperCase(),
                                fieldName.substring(1, fieldName.length()));
                        try {
                            Method methodName = clazz.getMethod(getMethodName);
                            if (methodName.isAnnotationPresent(OneToOne.class) || methodName.isAnnotationPresent(OneToMany.class)
                                    || methodName.isAnnotationPresent(ManyToOne.class) || methodName.isAnnotationPresent(ManyToMany.class)) {

                                return true;
                            }
                        } catch (NoSuchMethodException e) {
                            if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                                getMethodName = String.format("is%s%s", fieldName.substring(0, 1).toUpperCase(),
                                        fieldName.substring(1, fieldName.length()));
                                try {
                                    Method methodName = clazz.getMethod(getMethodName);
                                    if (methodName.isAnnotationPresent(OneToOne.class) || methodName.isAnnotationPresent(OneToMany.class)
                                            || methodName.isAnnotationPresent(ManyToOne.class) || methodName.isAnnotationPresent(ManyToMany.class)) {

                                        return true;
                                    }
                                } catch (NoSuchMethodException ne) {
                                    log.error("找不到" + fieldName + "的is方法，发返回json");
                                    return true;
                                }
                            } else {
                                log.error("找不到" + fieldName + "的get方法，发返回json");
                                return true;
                            }
                        }
                    }
                } catch (NoSuchFieldException e) {
                    log.debug("在当前类<" + clazz.getName() + ">找不到字段" + name + ", 到父类查找");
                    return applyParent(clazz.getSuperclass(), name, value);
                } catch (Exception e) {
                    log.error("处理失败<" + clazz.getName() + "." + name + ">, 直接返回值");
                    return false;
                }
                return false;
            }
        });
        // 处理日期在前台转换为object问题
        config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        // 处理Boolean为null情况在前台显示false问题
        config.registerJsonValueProcessor(Boolean.class, new JsonBooleanValueProcessor());
        // 处理Long为null时候被转为0的问题
        config.registerJsonValueProcessor(Long.class, new JsonLongValueProcessor());
        return config;
    }

    public static JsonConfig getExcludeConfig(String... excludeProperties) {
        return getExcludeConfig(null, excludeProperties);
    }

	public static JsonConfig getIncludeConfig(String... includeProperties) {
		return getIncludeConfig(null, includeProperties);
	}

	public static JsonConfig getIncludeConfig(final Class<?>[] includeClass, final String... includeProperties) {
		final JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {

			@Override
			public boolean apply(Object source, String name, Object value) {
				Class<? extends Object> clazz = source.getClass();
				try {
					Field field = clazz.getDeclaredField(name);
					if (includeClass != null) {
						for (Class<?> class1 : includeClass) {
							if (field.getType() == class1) {
								return false;
							}
						}
					}
					if (includeProperties != null) {
						for (String property : includeProperties) {
							if (field.getName().equals(property)) {
								return false;
							}
						}
					}
					if (field.isAnnotationPresent(OneToOne.class) || field.isAnnotationPresent(OneToMany.class)
							|| field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(ManyToMany.class)) {
						return true;
					}
					String fieldName = field.getName();
					if (fieldName.length() > 1) {
						String getMethodName = String.format("get%s%s", fieldName.substring(0, 1).toUpperCase(),
								fieldName.substring(1, fieldName.length()));
						try {
							Method methodName = clazz.getMethod(getMethodName);
							if (methodName.isAnnotationPresent(OneToOne.class) || methodName.isAnnotationPresent(OneToMany.class)
									|| methodName.isAnnotationPresent(ManyToOne.class) || methodName.isAnnotationPresent(ManyToMany.class)) {

								return true;
							}
						} catch (NoSuchMethodException e) {
							if (field.getType() == boolean.class || field.getType() == Boolean.class) {
								getMethodName = String.format("is%s%s", fieldName.substring(0, 1).toUpperCase(),
									fieldName.substring(1, fieldName.length()));
								try {
									Method methodName = clazz.getMethod(getMethodName);
									if (methodName.isAnnotationPresent(OneToOne.class) || methodName.isAnnotationPresent(OneToMany.class)
										|| methodName.isAnnotationPresent(ManyToOne.class) || methodName.isAnnotationPresent(ManyToMany.class)) {

										return true;
									}
								} catch (NoSuchMethodException ne) {
									log.error("找不到" + fieldName + "的is方法，发返回json");
									return true;
								}
							} else {
								log.error("找不到" + fieldName + "的get方法，发返回json");
								return true;
							}
						}
					}
				} catch (NoSuchFieldException e) {
					log.debug("在当前类<" + clazz.getName() + ">找不到字段" + name + ", 到父类查找");
					Boolean flag = applyParent(clazz.getSuperclass(), name, value);
					if (flag != null) {
						return flag.booleanValue();
					}
				} catch (Exception e) {
					log.error("处理失败<" + clazz.getName() + "." + name + ">, 直接返回值");
				}
				for (Class<?> interfaze : clazz.getInterfaces()) {
					if (interfaze == HibernateProxy.class) {
						return true;
					}
				}
				return false;
			}

			private Boolean applyParent(Class clazz, String name, Object value) {
				if (clazz == Object.class) {
					return null;
				}
				try {
					Field field = clazz.getDeclaredField(name);
					if (includeClass != null) {
						for (Class<?> class1 : includeClass) {
							if (field.getType() == class1) {
								return false;
							}
						}
					}
					if (includeProperties != null) {
						for (String property : includeProperties) {
							if (field.getName().equals(property)) {
								return false;
							}
						}
					}
					if (field.isAnnotationPresent(OneToOne.class) || field.isAnnotationPresent(OneToMany.class)
						|| field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(ManyToMany.class)) {
						return true;
					}
					String fieldName = field.getName();
					if (fieldName.length() > 1) {
						String getMethodName = String.format("get%s%s", fieldName.substring(0, 1).toUpperCase(),
							fieldName.substring(1, fieldName.length()));
						try {
							Method methodName = clazz.getMethod(getMethodName);
							if (methodName.isAnnotationPresent(OneToOne.class) || methodName.isAnnotationPresent(OneToMany.class)
								|| methodName.isAnnotationPresent(ManyToOne.class) || methodName.isAnnotationPresent(ManyToMany.class)) {

								return true;
							}
						} catch (NoSuchMethodException e) {
							if (field.getType() == boolean.class || field.getType() == Boolean.class) {
								getMethodName = String.format("is%s%s", fieldName.substring(0, 1).toUpperCase(),
									fieldName.substring(1, fieldName.length()));
								try {
									Method methodName = clazz.getMethod(getMethodName);
									if (methodName.isAnnotationPresent(OneToOne.class) || methodName.isAnnotationPresent(OneToMany.class)
										|| methodName.isAnnotationPresent(ManyToOne.class) || methodName.isAnnotationPresent(ManyToMany.class)) {

										return true;
									}
								} catch (NoSuchMethodException ne) {
									log.error("找不到" + fieldName + "的is方法，发返回json");
									return true;
								}
							} else {
								log.error("找不到" + fieldName + "的get方法，发返回json");
								return true;
							}
						}
					}
				} catch (NoSuchFieldException e) {
					log.debug("在当前类<" + clazz.getName() + ">找不到字段" + name + ", 到父类查找");
					return applyParent(clazz.getSuperclass(), name, value);
				} catch (Exception e) {
					log.error("处理失败<" + clazz.getName() + "." + name + ">, 直接返回值");
					return false;
				}
				return false;
			}
		});
		// 处理日期在前台转换为object问题
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		// 处理Boolean为null情况在前台显示false问题
		config.registerJsonValueProcessor(Boolean.class, new JsonBooleanValueProcessor());
        // 处理Long为null时候被转为0的问题
        config.registerJsonValueProcessor(Long.class, new JsonLongValueProcessor());
		// 处理Integer为null时候被转为0的问题
		config.registerJsonValueProcessor(Integer.class, new JsonIntegerValueProcessor());
		return config;
	}
}
