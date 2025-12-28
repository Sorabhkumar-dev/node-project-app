//
// Created by Beast on 28/12/25.
//

import Foundation
import UIKit
import ComposeApp // Import your KMP shared module

@main
class AppDelegate: UIResponder, UIApplicationDelegate {

    // Keep a reference to the scheduler
    let taskScheduler = PlatformTaskScheduler()

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {

        // 1. Register the task immediately
        taskScheduler.register()

        // 2. Schedule the first run (if it hasn't been scheduled yet)
        taskScheduler.scheduleSync()

        return true
    }
}