package Algorithms;

import java.util.ArrayList;
import java.util.List;

public class SearchStatistics {
    public long nodesVisited;
    public long maxNodes;
    public long minNodes;
    public long chanceNodes;
    public long terminalNodes;

    public double bestMoveValue;

    public List<String> trace = new ArrayList<>();

    public void reset() {
        nodesVisited = 0;
        maxNodes = 0;
        minNodes = 0;
        chanceNodes = 0;
        terminalNodes = 0;
        bestMoveValue = 0;
        trace.clear();
    }

    public String summary(String moveDescription, int depth) {
        return String.format(
                "[AI] %s | value=%.2f | nodes=%d (MAX=%d MIN=%d CHANCE=%d TERM=%d) | depth=%d",
                moveDescription,
                bestMoveValue,
                nodesVisited,
                maxNodes,
                minNodes,
                chanceNodes,
                terminalNodes,
                depth);
    }
}