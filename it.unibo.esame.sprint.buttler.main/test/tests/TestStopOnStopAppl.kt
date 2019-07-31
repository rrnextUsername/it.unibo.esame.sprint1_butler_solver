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

class TestStopOnStopAppl {
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
		println(" %%%%%%% TestButtler  stopOnStopApplTest ")
		
		sendCmdMessage(resource!!,1000)
		sendStopApplMessage(resource!!,2000)
		
		solveCheckGoal(resource!!,"stato( stoppedSolvedAction )")
 	}
//----------------------------------------
	 	
	fun solveCheckGoal( actor : ActorBasic, goal : String ){
		actor.solve( goal  )
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

	fun delay( time : Long ){
		Thread.sleep( time )
	}


}
