package com.zuipin.util;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class HibernateValidateUtils
{
  private static Validator validator;

  static
  {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    validator = validatorFactory.getValidator();
  }

  public static <T> Set<ConstraintViolation<T>> getValidator(T t)
  {
    return validator.validate(t, new Class[0]);
  }

  public static <T> Set<ConstraintViolation<T>> getValidatorProperty(T t, String property)
  {
    return validator.validateProperty(t, property, new Class[0]);
  }
}