(function() {
    'use strict';

    angular
        .module('app')
        .controller('UniteDialogController', UniteDialogController);

    UniteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Unite'];

    function UniteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Unite ) {
        var vm = this;

        vm.unite = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        
      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.unite.id !== null) {
                Unite.update(vm.unite, onSaveSuccess, onSaveError);
            } else {
                Unite.save(vm.unite, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:uniteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        
        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

    }
})();
