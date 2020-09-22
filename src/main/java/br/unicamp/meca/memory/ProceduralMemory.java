package br.unicamp.meca.memory;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryContainer;
import br.unicamp.cst.core.entities.MemoryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProceduralMemory {

    private String id;

    private MemoryContainer memories;

    public ProceduralMemory(String id) {
        this.id = id;
        memories = new MemoryContainer(id);
    }

    public void addMemory(Memory memory){
        memories.add(memory);
    }

    public Memory getProceduralMemory(String memoryId) throws Exception {
        Optional<Memory> optionalMemory =
                memories.getAllMemories().stream().filter(memory -> memory.getI().equals(memoryId)).findFirst();

        return optionalMemory.orElseThrow(() -> new Exception("Procedural memory not found."));
    }
}
