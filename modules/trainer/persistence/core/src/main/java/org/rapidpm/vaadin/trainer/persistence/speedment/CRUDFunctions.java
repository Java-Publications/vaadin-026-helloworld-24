package org.rapidpm.vaadin.trainer.persistence.speedment;

import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.core.manager.Manager;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.Transformations;
import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.trainer.api.model.CalcResult;
import org.rapidpm.vaadin.trainer.api.model.User;
import org.rapidpm.vaadin.trainer.persistence.speedment.core.user.UserFunctions;
import org.rapidpm.vaadin.trainer.persistence.speedment.mainview.calc.CalcResultFunctions;
import org.rapidpm.vaadin.trainer.persistence.speedment.postgres.public_.comp_math_basic.CompMathBasic;
import org.rapidpm.vaadin.trainer.persistence.speedment.postgres.public_.comp_math_basic.CompMathBasicImpl;

import java.sql.Timestamp;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.rapidpm.vaadin.trainer.persistence.speedment.postgres.public_.core_user.generated.GeneratedCoreUser.LOGIN;

/**
 *
 */
public interface CRUDFunctions extends
                               UserFunctions,
                               CalcResultFunctions,
                               HasLogger,
                               HasSpeedmentApp {


  default Function<String, Stream<CalcResult>> calResultsForLogin(){
    return (login) -> _mathBasicsForLogin()
        .apply(login)
        .map(mapCompMathBasic());
  }

  default Function<String, Stream<CompMathBasic>> _mathBasicsForLogin() {
    return (login) -> _coreUsers()
        .apply(app())
        .filter(LOGIN.equal(login))
        .flatMap(_mathBasicManager()
                     .apply(app())
                     .finderBackwardsBy(CompMathBasic.ID_USER));
  }

  default Function<CompMathBasic, CalcResult> mapCompMathBasic() {
    return (input) -> {
      final CalcResult result = new CalcResult();
      result.setId(input.getId());
      result.setOpA(input.getOpA());
      result.setOpB(input.getOpB());
      result.setOperator(input.getOp());
      result.setResultHuman(input.getResultHuman().orElse(""));
      result.setResultMachine(input.getResultMachine());
      result.setResultOK(input.getResultOk());
      result.setTimestamp(input.getCreated().toLocalDateTime());
      final long userID = input.getIdUser().orElse(-1L);
      ((userID == -1L)
       ? Result.<User>failure("no userID id : " + userID)
       : userWithID().apply(userID)
      ).ifPresentOrElse(
          result::setUser,
          failed -> logger().info(failed)
      );
      return result;
    };
  }

  default Function<CalcResult, CompMathBasic> mapCalcResult() {
    return (input) -> {
      final CompMathBasic result = new CompMathBasicImpl()
          .setCreated((input.getTimestamp() == null) ? null : Timestamp.valueOf(input.getTimestamp()))
          .setOp(input.getOperator())
          .setOpA(input.getOpA())
          .setOpB(input.getOpB())
          .setResultHuman(input.getResultHuman())
          .setResultMachine(input.getResultMachine())
          .setResultOk(input.getResultOK())
          .setIdUser((input.getUser() == null) ? null : input.getUser().id());

      if (input.getId() != null) {
        result.setId(input.getId());
      }

      return result;

    };
  }

  default Consumer<CalcResult> saveOrUpdateCalcResult() {
    return (result) -> ((result.getId() == null || result.getId() == -1L)
                        ? _persistCalcResult()
                        : _updateCalcResult())
        .accept(result);
  }


  default Consumer<CalcResult> _persistCalcResult() {
    return (result) -> _mathBasicManager()
        .andThen(Manager::persister)
        .andThen(consumer -> {
          consumer.accept(mapCalcResult().apply(result));
          return null;
        })
        .apply(app());
  }

  default Consumer<CalcResult> _updateCalcResult() {
    return (result) -> _mathBasicManager()
        .andThen(Manager::updater)
        .andThen(consumer -> {
          consumer.accept(mapCalcResult().apply(result));
          return null;
        })
        .apply(app());
  }

}
