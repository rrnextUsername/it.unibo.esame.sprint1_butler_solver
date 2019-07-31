package tests

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlinx.coroutines.GlobalScope
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.launch
import it.unibo.kactor.sysUtil
import org.junit.jupiter.api.AfterEach
import it.unibo.kactor.MsgUtil

class TestConsistencyOnRestartAppl {
  var resource : ActorBasic? = null
	
	@BeforeEach
	fun systemSetUp() {
  	 		GlobalScope.launch{ //activate an observer ...
 				itunibo.coap.observer.main()		//blocking
 			}	
  	 		GlobalScope.launch{
 			    println(" %%%%%%% TestButtler starts buttler mind ")
				it.unibo.ctxButler.main()
 			}
			delay(5000)		//give the time to start
			resource = sysUtil.getActor("butler")	
		    println(" %%%%%%% TestButtler getActors resource=${resource}")
 	}
 
	@AfterEach
	fun terminate() {
		println(" %%%%%%% TestButtler terminate ")
	}
 
	@Test
	fun startInWaitTest() {
		println(" %%%%%%% TestButtler  consistencyOnRestartApplTest ")
		sendCmdMessage(resource!!,1000)
		
		//test stop
		sendStopApplMessage(resource!!,2000)
		solveCheckGoal(resource!!,"stato( stoppedSolvedAction )")
		
		//test restart
		sendRestartApplMessage(resource!!,1000)
		solveCheckGoal(resource!!,"done( restartSolvedAction )")
		delay(5000)
		
		//test all done in order
		solveCheckGoalOrder(resource!!,"done( check, NUMBER )","1")
		solveCheckGoalOrder(resource!!,"done( wait, NUMBER )","1")
		solveCheckGoalOrder(resource!!,"done( check, NUMBER )","2")
		
		//test no others done
		solveCheckGoalNoOthers(resource!!,"done( check, _ )")
		solveCheckGoalNoOthers(resource!!,"done( wait, _ )")
 	}
//----------------------------------------
	
	fun solveCheckGoalOrder( actor : ActorBasic, goal : String, NUMBER: String ){
		actor.solve( "retract( $goal )", "NUMBER" )
		
		var result =  actor.resVar
		
		println(" %%%%%%%  actor={$actor.name} goal= $goal  result = $result")
		assertTrue(result=="$NUMBER","%%%%%%% TestButtler expected '$NUMBER' found $result")
	}
	
	fun solveCheckGoalNoOthers(actor : ActorBasic, goal : String){
		actor.solve( "retract($goal)"  )
		var result =  actor.resVar
		println(" %%%%%%%  actor={$actor.name} goal= $goal  result = $result")
		assertTrue(result=="fail","%%%%%%% TestButtler expected 'fail' found $result")
	}
	
	fun solveCheckGoal( actor : ActorBasic, goal : String ){
		actor.solve( "retract($goal)"  )
		var result =  actor.resVar
		println(" %%%%%%%  actor={$actor.name} goal= $goal  result = $result")
		assertTrue(result=="success","%%%%%%% TestButtler expected 'success' found $result")
	}
	
	fun sendCmdMessage( actor : ActorBasic, time : Long ){
			println("--- sendQueryMessage comando( test )")
		actor.scope.launch{
			MsgUtil.sendMsg("cmd", "cmd( test )" ,actor ) 
 		}
		delay(time) //give time to do the move
  	}
	
	fun sendStopApplMessage( actor : ActorBasic, time : Long ){
			println("--- sendQueryMessage stopAppl")
		actor.scope.launch{
			MsgUtil.sendMsg("stopAppl", "stopAppl" ,actor ) 
 		}
		delay(time) //give time to do the move
  	}
	
	fun sendRestartApplMessage( actor : ActorBasic, time : Long ){
			println("--- sendQueryMessage reactivateAppl")
		actor.scope.launch{
			MsgUtil.sendMsg("reactivateAppl", "reactivateAppl" ,actor ) 
 		}
		delay(time) //give time to do the move
  	}

	fun delay( time : Long ){
		Thread.sleep( time )
	}


}
