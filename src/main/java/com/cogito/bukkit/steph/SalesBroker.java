package com.cogito.bukkit.steph;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cogito.bukkit.conversations.ConversationAgent;
import com.cogito.bukkit.conversations.ConversationEndReason;
import com.cogito.bukkit.conversations.ConversationListener;
import com.cogito.bukkit.conversations.Message;
import com.cogito.bukkit.conversations.Question;

public class SalesBroker implements ConversationListener{

    private static List<String> names = Arrays.asList("Anne", "Janet", "Henry", "Betty", "Joseph", "Richard");

    private String name;

    private SalesTransaction sale;

    private ConversationAgent agent;

    private final String initiator;

    private STEPH plugin;

    public SalesBroker(STEPH plugin, String initiator) {
        super();
        this.plugin = plugin;
        this.initiator = initiator;
    }

    /**
     * When a player has an active teller session, this message will be sent if they type something other
     * than a response.
     */
    private void printDialogueError() {
        agent.sendMessage(new Message(initiator, "You have a transaction waiting for approval. Type 'yes' to approve"));
    }

    boolean restartInteractive() {
        agent.sendQuestion(new SalesTransactionQuestion(initiator, "How may I help you now?"));
        return true;
    }

    public void startInteractiveSession() {
        agent.sendQuestion(new SalesTransactionQuestion(initiator, "How may I help you?"));
    }

    public String toString(){
        return initiator+"_SalesBroker"+Integer.toHexString(hashCode());
    }

    public void onConversationEnd(ConversationEndReason reason, Queue<Message> messages) {
        // TODO Auto-generated method stub
    }

    public boolean onReply(Question question, String reply) {
        return parseReply(question, reply.split(" "));
    }

    public boolean parseReply(Question question, String[] reply) {
        if (question == null) {
            question = new SalesTransactionQuestion(initiator, "");
        } else if (question instanceof SalesTransactionQuestion) {
        } else {
            return false;
        }
        SalesTransactionQuestion stQuestion = (SalesTransactionQuestion) question;
        Iterator<String> itr = Arrays.asList(reply).iterator();
        for (; itr.hasNext();) {
            String r = itr.next();
            if (r.equalsIgnoreCase("sell")) {
                stQuestion.creditor = plugin.bank().getAccount(initiator);
            } else if (r.equalsIgnoreCase("buy")) {
                stQuestion.debitor = plugin.bank().getAccount(initiator);
                continue;
            }
        }
        return true;
    }

    public String getName() {
        if (this.name == null) {
            Random random = new Random();
            this.name = names.get(random.nextInt(names.size()));
        }
        return this.name;
    }

    public ChatColor nameColor() {
        return ChatColor.BLUE;
    }
    public ChatColor messageColor() {
        return ChatColor.BLUE;
    }

    public void setAgent(ConversationAgent agent) {
        this.agent = agent;
    }
}
