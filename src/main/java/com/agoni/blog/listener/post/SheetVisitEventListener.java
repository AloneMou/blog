package com.agoni.blog.listener.post;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.agoni.blog.event.post.SheetVisitEvent;
import com.agoni.blog.service.SheetService;

/**
 * Sheet visit event listener.
 *
 * @author johnniang
 * @date 19-4-24
 */
@Component
public class SheetVisitEventListener extends AbstractVisitEventListener {

    protected SheetVisitEventListener(SheetService sheetService) {
        super(sheetService);
    }

    @Async
    @EventListener
    public void onSheetVisitEvent(SheetVisitEvent event) throws InterruptedException {
        handleVisitEvent(event);
    }

}
