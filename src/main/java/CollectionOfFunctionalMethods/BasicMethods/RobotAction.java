package CollectionOfFunctionalMethods.BasicMethods;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RobotAction {
    /**
     * 模拟键盘按下ctrl+w 模拟关闭web（调用键盘快捷方式只会关闭当前的浏览器页面）
     *
     * @throws AWTException
     */
    public static void CloseWindow() throws AWTException {
        Robot keyboard = new Robot();
        keyboard.keyPress(KeyEvent.VK_CONTROL);
        keyboard.keyPress(KeyEvent.VK_W);
    }
    /**
     * 模拟键盘按下暂停模拟关闭web
     *
     * @throws AWTException
     */
    public static void PauseWindow() throws AWTException {
        Robot keyboard = new Robot();
        keyboard.keyPress(KeyEvent.VK_SPACE);
    }
    /**
     * 控制鼠标滚轮滚动
     * Auth WZB
     **/
    public static void ScrollWindow(int scale) throws AWTException {
        Robot Mouse = new Robot();
        Mouse.mouseWheel(scale);
        System.out.print("Scroll  the mouse "+scale);
    }
    /**
     * 控制鼠标位置移动
     * Auth WZB
     * x 横坐标 y 纵坐标 type=0 坐标为x,y
     **/

    public static void MouseMovement(int type, int x, int y) {
        Point p = MouseInfo.getPointerInfo().getLocation();
        System.out.println("mouse =:"+p.getX() + "---" +p.getY());
        int width = (int) p.getX() + x;
        int heigh = (int) p.getY() + y;
        if (type == 0) {
            width = x;
            heigh = y;
        }
        Robot robot;
        try {
            robot = new Robot();
            robot.mouseMove(width, heigh);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

}
