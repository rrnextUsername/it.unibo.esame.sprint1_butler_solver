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

class TestStartInWaitCmd {
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
		println(" %%%%%%% TestButtler  waitCmdTest ")
		solveCheckGoal(resource!!,"stato( waitCmd )")
 	}
//----------------------------------------
	 	
	fun solveCheckGoal( actor : ActorBasic, goal : String ){
		actor.solve( goal  )
		var result =  actor.resVar
		println(" %%%%%%%  actor={$actor.name} goal= $goal  result = $result")
		assertTrue(result=="success","%%%%%%% TestButtler expected 'success' found $result")
	}

	fun delay( time : Long ){
		Thread.sleep( time )
	}


}
