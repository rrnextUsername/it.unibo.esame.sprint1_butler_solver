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

class TestSolveCmd {
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
	fun addSolveCmd() {
		println(" %%%%%%% TestButtler  solveCmdTest ")
		sendCmdMessage(resource!!,5000)
		
		//completeded all tasks
		solveCheckGoal(resource!!,"done( check, 1 )")
		solveCheckGoal(resource!!,"done( wait, 1 )")
		solveCheckGoal(resource!!,"done( check, 2 )")
		
		//returned to waitCmd
		solveCheckGoal(resource!!,"stato( waitCmd )")
 	}
	
//----------------------------------------
	 	
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

	fun delay( time : Long ){
		Thread.sleep( time )
	}


}
