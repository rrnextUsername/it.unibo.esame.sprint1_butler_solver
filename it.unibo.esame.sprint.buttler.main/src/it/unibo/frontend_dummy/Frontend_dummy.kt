/* Generated by AN DISI Unibo */ 
package it.unibo.frontend_dummy

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Frontend_dummy ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						solve("consult('sysRules.pl')","") //set resVar	
					}
					 transition( edgeName="goto",targetState="waitReply", cond=doswitch() )
				}	 
				state("waitReply") { //this:State
					action { //it:State
					}
					 transition(edgeName="t00",targetState="persistReply",cond=whenEvent("modelContent"))
				}	 
				state("persistReply") { //this:State
					action { //it:State
						itunibo.frontend_dummy.frontend_dummySupport.persist(myself ,currentMsg )
					}
					 transition( edgeName="goto",targetState="waitReply", cond=doswitch() )
				}	 
			}
		}
}
