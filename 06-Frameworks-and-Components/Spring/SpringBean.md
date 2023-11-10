---
title: SpringBean
keywords: SpringBean
summary: SpringBean
tags:
  - spring
categories:
  - 06-Frameworks-and-Components
  - Spring
date: 2023-11-10 11:27:00
---

## 重写bean实例

### 自定义bean覆盖

```java

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    private static String SPECIAL_OVERRIDE_BEAN = "healthMessageQueueLogService";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        boolean isContainsSpecialBean = ((DefaultListableBeanFactory) registry).containsBean(SPECIAL_OVERRIDE_BEAN);
        if (isContainsSpecialBean) {
            AnnotatedBeanDefinition healthMessageBeanDefinition = (AnnotatedBeanDefinition) registry.getBeanDefinition(SPECIAL_OVERRIDE_BEAN);
            if (healthMessageBeanDefinition != null) {
                AnnotatedGenericBeanDefinition myBeanDefinition = new AnnotatedGenericBeanDefinition(healthMessageBeanDefinition.getMetadata());
                //忽略 beanClass originatingBeanDefinition字段
                BeanUtils.copyProperties(healthMessageBeanDefinition, myBeanDefinition, "beanClass", "originatingBeanDefinition");
                //设置自定义的bean class
                myBeanDefinition.setBeanClass(MyHealthMessageQueueLogService.class);
                //重新加载自定义的bean class
                try {
                    myBeanDefinition.resolveBeanClass(Thread.currentThread().getContextClassLoader());
                    registry.registerBeanDefinition(SPECIAL_OVERRIDE_BEAN, myBeanDefinition);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
```

### 自定义移除重写的bean

```java
@ComponentScan(
        basePackages = {"com.basic", "com.system", "com.business"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.zh.core.service.SysUserBasicService"))
```
