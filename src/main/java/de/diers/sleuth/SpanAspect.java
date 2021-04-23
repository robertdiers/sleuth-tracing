package de.diers.sleuth;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import brave.Span;
import brave.Tracer;

@Aspect
@Component
public class SpanAspect {   

   @Autowired
   private Tracer tracer;

   @Value("${sping.brave.custom.span-per-service}")
   private Boolean spanPerService;

   @Value("${sping.brave.custom.span-per-controller}")
   private Boolean spanPerController;
    
   @Around("within(@org.springframework.stereotype.Service *)")
   public Object serviceNewSpan(ProceedingJoinPoint joinPoint) throws Throwable {         
      if (spanPerService != null && spanPerService) {
         return newSpan(joinPoint);
      }
      return joinPoint.proceed();
   }

   @Around("within(@org.springframework.stereotype.Controller *)")
   public Object controllerNewSpan(ProceedingJoinPoint joinPoint) throws Throwable {         
      if (spanPerController != null && spanPerController) {
         return newSpan(joinPoint);
      }
      return joinPoint.proceed();
   }

   /**
    * create a new span for every Service / Controller
    * @param joinPoint
    * @return result
    * @throws Throwable
    */
   public Object newSpan(ProceedingJoinPoint joinPoint) throws Throwable {

      //create new Span
      String packageName = joinPoint.getSignature().getDeclaringTypeName();      
      String methodName = joinPoint.getSignature().getName();
      Span newSpan = this.tracer.nextSpan().name(packageName+"."+methodName);

      Object result = null;
      try (Tracer.SpanInScope ws = this.tracer.withSpanInScope(newSpan.start())) {
         result =  joinPoint.proceed();
      }
      finally {
          newSpan.finish();            
      }     

      return result;
   }

}